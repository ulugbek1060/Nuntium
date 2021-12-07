package com.example.nuntium.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.nuntium.R
import com.example.nuntium.util.MySharedPreference
import com.example.nuntium.util.ThemeHelper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val themeChecker = MySharedPreference(this).getThemeChecker()

        if (themeChecker == ThemeHelper.lightMode) {
            ThemeHelper.applyTheme(ThemeHelper.lightMode)
        } else {
            ThemeHelper.applyTheme(ThemeHelper.darkMode)
        }


        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }, 1000)
    }
}