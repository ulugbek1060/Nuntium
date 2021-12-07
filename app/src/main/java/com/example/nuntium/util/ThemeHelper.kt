package com.example.nuntium.util

import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    const val lightMode = "light"
    const val darkMode = "dark"
    const val batterySaveMode = "battery"
    const val default = "default"

    fun applyTheme(theme: String) {
        when (theme) {
            lightMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            batterySaveMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            default -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}