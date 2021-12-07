package com.example.nuntium.util

import android.content.Context

const val FILE_NAME = "configuration"

class MySharedPreference(context: Context) {
    private val appSharedPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun setActiveChecker(value: Boolean) {
        val prefsEditor = appSharedPrefs?.edit()
        prefsEditor?.putBoolean(IS_ACTIVE, value)
        prefsEditor?.apply()
    }

    fun getActiveChecker(): Boolean {
        return appSharedPrefs?.getBoolean(IS_ACTIVE, false)!!
    }

    fun setThemeChecker(value: String) {
        val prefsEditor = appSharedPrefs?.edit()
        prefsEditor?.putString(IS_COVERED, value)
        prefsEditor?.apply()
    }

    fun getThemeChecker(): String {
        return appSharedPrefs?.getString(IS_COVERED, ThemeHelper.lightMode) ?: ThemeHelper.lightMode
    }

    fun setLanguage(value: String) {
        val prefsEditor = appSharedPrefs?.edit()
        prefsEditor?.putString(LANGUAGE, value)
        prefsEditor?.apply()
    }

    fun getLanguage(): String {
        return appSharedPrefs?.getString(LANGUAGE, ENGLISH) ?: ENGLISH
    }


    fun clearList() {
        appSharedPrefs.edit().clear().apply()
    }
}
