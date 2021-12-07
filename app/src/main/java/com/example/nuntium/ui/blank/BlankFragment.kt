package com.example.nuntium.ui.blank

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.PageAdapter
import com.example.nuntium.databinding.FragmentBlankBinding
import com.example.nuntium.response.Article
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.UtilList.removeNullable
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class BlankFragment : ContainsFragment(R.layout.fragment_blank), CoroutineScope {

    @Inject
    lateinit var viewMode: BlankViewModel
    private val binding by viewBinding(FragmentBlankBinding::bind)
    private lateinit var adapter: PageAdapter
    private var param1: String? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        param1 = arguments?.getString("ARG_PARAM1")
        App.appComponent.injectBlankFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkState().observe(viewLifecycleOwner, {
            if (!it)
                binding.connectionChecker.visibility = View.VISIBLE
            else
                binding.connectionChecker.visibility = View.GONE
        })

        adapter = PageAdapter(listener)

        binding.recyclerViewVertical.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewVertical.layoutManager = layoutManager

        binding.recyclerViewVertical.adapter = adapter

        viewMode.getSourceByQuery(param1!!)

        viewMode.category.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private val listener = object : PageAdapter.OnItemClickListener {
        override fun onItemClick(article: Article) {
            removeNullable(article)
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                }
            }
    }
}