package com.example.nuntium

import android.app.Application
import com.example.nuntium.di.commponent.AppComponent
import com.example.nuntium.di.commponent.DaggerAppComponent
import com.example.nuntium.di.module.DatabaseModule
import com.example.nuntium.util.MySharedPreference
import com.yariksoffice.lingver.Lingver


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val language = MySharedPreference(this).getLanguage()
        Lingver.init(this, language)

        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}