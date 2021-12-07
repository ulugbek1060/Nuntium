package com.example.nuntium.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nuntium.db.entity.Category
import com.example.nuntium.ui.blank.BlankFragment
import com.example.nuntium.ui.random.RandomFragment

class HomeViewPagerAdapter(
    fragmentManager: FragmentManager, val list: List<Category>, lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            RandomFragment()
        } else {
            BlankFragment.newInstance(list[position].type.split(" ")[1])
        }
    }

//    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
//        super.setStateRestorationPolicy(strategy)
//
//    }
}