package com.example.nuntium.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.HomeViewPagerAdapter
import com.example.nuntium.databinding.FragmentHomeBinding
import com.example.nuntium.db.entity.Category
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.NetworkConnection
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject


class HomeFragment : ContainsFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: HomeViewPagerAdapter
    private lateinit var networkConnection: NetworkConnection

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectHomeFragment(this)
        networkConnection = NetworkConnection(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNetworkState()

        viewModel.getCategoryList().observe(viewLifecycleOwner, { list ->
            val filter = list.filter { category -> category.isHave } as ArrayList
            filter.add(0, Category("\uD83D\uDC57 Random", false))
            adapter =
                HomeViewPagerAdapter(childFragmentManager, filter, viewLifecycleOwner.lifecycle)
            binding.viewPager.adapter = adapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = filter[position].type.split(" ")[1]
            }.attach()
        })

        binding.edSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


}