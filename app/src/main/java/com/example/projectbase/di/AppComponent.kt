package com.example.projectbase.di

import com.example.projectbase.MainActivity
import com.example.projectbase.views.GameFragment
import com.example.projectbase.views.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity:MainActivity)
    fun inject (loginFragment: LoginFragment)
    fun inject (gameFragment: GameFragment)
}