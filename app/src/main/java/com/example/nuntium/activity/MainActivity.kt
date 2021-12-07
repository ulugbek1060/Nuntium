package com.example.nuntium.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nuntium.R
import com.example.nuntium.databinding.ActivityMainBinding
import com.example.nuntium.util.MySharedPreference
import com.example.nuntium.util.ThemeHelper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var checkState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themeChecker = MySharedPreference(this).getThemeChecker()

        if (themeChecker == ThemeHelper.lightMode) {
            ThemeHelper.applyTheme(ThemeHelper.lightMode)
        } else {
            ThemeHelper.applyTheme(ThemeHelper.darkMode)
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        val isActive = MySharedPreference(this).getActiveChecker()

        if (!isActive) {
            navController.navigate(R.id.introductionFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.categoryFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.bookmarkFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.cabinetFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.introductionFragment -> {
                    checkState = false
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.selectionCategoryFragment -> {
                    checkState = false
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.articleFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.languageFragment -> {
                    checkState = true
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        if (checkState) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(
            this,
            R.id.container_fragment
        ).navigateUp() || super.onNavigateUp()
    }
}