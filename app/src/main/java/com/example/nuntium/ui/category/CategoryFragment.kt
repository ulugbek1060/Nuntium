package com.example.nuntium.ui.category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.CategoryAdapter
import com.example.nuntium.databinding.FragmentCategoryBinding
import com.example.nuntium.db.entity.Category
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.MySharedPreference
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryFragment : ContainsFragment(R.layout.fragment_category) {

    private val binding by viewBinding(FragmentCategoryBinding::bind)
    private lateinit var adapter: CategoryAdapter

    @Inject
    lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectCategoryFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryAdapter(listener, MySharedPreference(requireContext()).getThemeChecker())
        binding.recyclerView.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private val listener = object : CategoryAdapter.OnClickListener {
        override fun onItemClickListener(category: Category, position: Int) {
            selection(category, position)
        }
    }

    private fun selection(category: Category, position: Int) {
        launch {
            val count = viewModel.getCount()
            if (count >= 3 && !category.isHave) {
                category.isHave = true
                viewModel.update(category)
                adapter.notifyItemChanged(position)
            } else if (count > 3 && category.isHave) {
                category.isHave = false
                viewModel.update(category)
                adapter.notifyItemChanged(position)
            } else if (count <= 3 && category.isHave) {
                Toast.makeText(
                    requireContext(),
                    "the selected item must be less then 3",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}