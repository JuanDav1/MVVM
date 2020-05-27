package com.example.projectbase.di

import com.example.projectbase.App
import com.example.projectbase.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity:MainActivity)
}