package com.example.projectbase

import android.app.Application
import com.example.projectbase.di.AppComponent
import com.example.projectbase.di.AppModule
import com.example.projectbase.di.DaggerAppComponent
import com.example.projectbase.di.UtilsModule


class App : Application() {

    private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent  =
            DaggerAppComponent.builder().appModule(AppModule(this)).utilsModule(UtilsModule())
                .build()
    }

    fun getAppComponent() : AppComponent {
        return appComponent
    }
    companion object {

        var context: App? = null
            private set
    }
}