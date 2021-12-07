package com.example.nuntium.ui.web

import android.os.Bundle
import android.view.View
import com.example.nuntium.R
import com.example.nuntium.databinding.FragmentWebBinding
import com.example.nuntium.ui.manager.ContainsFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class WebFragment : ContainsFragment(R.layout.fragment_web) {

    private val binding by viewBinding(FragmentWebBinding::bind)
    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString("url")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.loadUrl(url!!)
    }
}