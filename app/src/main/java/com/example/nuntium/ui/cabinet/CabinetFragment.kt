package com.example.nuntium.ui.cabinet

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.nuntium.R
import com.example.nuntium.databinding.FragmentCabinetBinding
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.MySharedPreference
import com.example.nuntium.util.ThemeHelper
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class CabinetFragment : ContainsFragment(R.layout.fragment_cabinet) {

    private val binding by viewBinding(FragmentCabinetBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeChecker = MySharedPreference(requireContext()).getThemeChecker()
        binding.btnSwitch.isChecked = themeChecker != ThemeHelper.lightMode

        binding.layout2.setOnClickListener {
            findNavController().navigate(R.id.action_cabinetFragment_to_languageFragment)
        }

        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                ThemeHelper.applyTheme(ThemeHelper.darkMode)
                MySharedPreference(requireContext()).setThemeChecker(ThemeHelper.darkMode)
            } else {
                ThemeHelper.applyTheme(ThemeHelper.lightMode)
                MySharedPreference(requireContext()).setThemeChecker(ThemeHelper.lightMode)
            }
        }
    }
}