package com.example.nuntium.ui.cabinet

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.nuntium.R
import com.example.nuntium.databinding.FragmentLanguageBinding
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.*
import com.yariksoffice.lingver.Lingver
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LanguageFragment : ContainsFragment(R.layout.fragment_language) {

    private val binding by viewBinding(FragmentLanguageBinding::bind)
    private lateinit var mySharedPreference: MySharedPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySharedPreference = MySharedPreference(requireContext())

        if (mySharedPreference.getThemeChecker() == ThemeHelper.darkMode) {
            binding.btnBack.setImageResource(R.drawable.ic_left_icon)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        when (mySharedPreference.getLanguage()) {
            ENGLISH -> {
                binding.btnEn.isChecked = true
            }
            GERMAN -> {
                binding.btnDe.isChecked = true
            }
            SPANISH -> {
                binding.btnSe.isChecked = true
            }
            TURK -> {
                binding.btnTr.isChecked = true
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_en -> {
                    setLanguage(ENGLISH)
                }
                R.id.btn_tr -> {
                    setLanguage(TURK)
                }
                R.id.btn_de -> {
                    setLanguage(GERMAN)
                }
                R.id.btn_se -> {
                    setLanguage(SPANISH)
                }
            }
        }
    }

    private fun setLanguage(lan: String) {
        mySharedPreference.setLanguage(lan)
        Lingver.getInstance().setLocale(requireContext(), lan)
        updateText()
    }

    private fun updateText() {
        binding.apply {
            btnEn.text = getText(R.string.english)
            btnDe.text = getText(R.string.german)
            btnTr.text = getText(R.string.turkish)
            btnSe.text = getText(R.string.spanish)
            tvLanguage.text = getText(R.string.language)
        }
    }

}