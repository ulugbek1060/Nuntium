package com.example.nuntium.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager.widget.ViewPager
import com.example.nuntium.R
import com.example.nuntium.adapters.splash.IntoAdapter
import com.example.nuntium.databinding.FragmentIntroductionBinding
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.UtilList.getIntroductionList
import com.ocnyang.pagetransformerhelp.cardtransformer.AlphaAndScalePageTransformer
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class IntroductionFragment : ContainsFragment(R.layout.fragment_introduction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IntoAdapter()

        adapter.setImageList(getIntroductionList())

        binding.viewPager.adapter = adapter

        binding.dotsIndicator.setViewPager(binding.viewPager)

        viewSetPageTransformer(binding.viewPager)

        binding.btnNext.setOnClickListener {
            funNavigate()
        }


    }

    private fun funNavigate() {
        if (!isClicked) {
            binding.dotsIndicator.startAnimation(alphaHide)
            binding.viewPager.startAnimation(alphaHide)
            binding.ivConfirm.startAnimation(alphaShow)
            binding.btnNext.text = "Get Started"
            binding.tv1.text = "Nuntium"
            isClicked = true
        } else {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(android.R.anim.slide_in_left)
                .setExitAnim(android.R.anim.slide_out_right)
                .build()

            NavHostFragment.findNavController(this@IntroductionFragment).navigate(
                R.id.action_introductionFragment_to_selectionCategoryFragment,
                null,
                navOptions
            )
        }
    }

    private fun viewSetPageTransformer(viewPager: ViewPager) {
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(true, AlphaAndScalePageTransformer())
    }

    private var isClicked = false
    private val binding by viewBinding(FragmentIntroductionBinding::bind)
    private lateinit var adapter: IntoAdapter

    private val alphaHide: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.alpha_hide
        )
    }

    private val alphaShow: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.alpha_show
        )
    }
}