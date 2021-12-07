package com.example.nuntium.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.CategoryAdapter
import com.example.nuntium.databinding.FragmentSelectionCategoryBinding
import com.example.nuntium.db.entity.Category
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.MySharedPreference
import com.example.nuntium.util.UtilList
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionCategoryFragment : ContainsFragment(R.layout.fragment_selection_category) {

    private val binding by viewBinding(FragmentSelectionCategoryBinding::bind)
    private lateinit var adapter: CategoryAdapter
    private var clickable = false

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectSelectFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            viewModel.insetList(UtilList.getList())
        }

        val themeChecker = MySharedPreference(requireContext()).getThemeChecker()

        adapter = CategoryAdapter(listener, themeChecker)

        adapter.submitList(UtilList.getList())

        binding.recyclerView.adapter = adapter

        binding.btnNext.setOnClickListener {
            writeToSharedPref()
        }
    }

    private fun writeToSharedPref() {
        if (clickable) {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(android.R.anim.slide_in_left)
                .setExitAnim(android.R.anim.slide_out_right)
                .setPopUpTo(R.id.homeFragment, true)
                .build()
            NavHostFragment.findNavController(this@SelectionCategoryFragment)
                .navigate(
                    R.id.action_selectionCategoryFragment_to_homeFragment,
                    null,
                    navOptions
                )
            MySharedPreference(requireContext()).setActiveChecker(true)
        } else {
            Toast.makeText(requireContext(), "choose 3 items", Toast.LENGTH_SHORT).show()
        }
    }

    private val listener = object : CategoryAdapter.OnClickListener {
        override fun onItemClickListener(category: Category, position: Int) {
            category.isHave = !category.isHave
            adapter.notifyItemChanged(position)
            launch {
                viewModel.update(category)
            }
            checkItems()
        }
    }

    private fun checkItems() {
        val filter = adapter.currentList.filter { category -> category.isHave }
        if (filter.size >= 3) {
            clickable = true
            binding.btnNext.setBackgroundResource(R.drawable.btn_back)
        } else {
            clickable = false
            binding.btnNext.setBackgroundResource(R.drawable.btn_back_off)
        }
    }
}