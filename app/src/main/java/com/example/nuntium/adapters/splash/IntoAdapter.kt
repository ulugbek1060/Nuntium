package com.example.nuntium.adapters.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.nuntium.databinding.ItemIntroduceBinding

class IntoAdapter : PagerAdapter() {

    private val list = ArrayList<Int>()

    fun setImageList(source: List<Int>) {
        list.addAll(source)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            ItemIntroduceBinding.inflate(LayoutInflater.from(container.context), container, false)
        binding.ivIntroduction.setImageResource(list[position])
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}