package com.example.projectbase.di

import android.content.Context
import com.example.projectbase.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {


@Provides
@Singleton
fun provideContext(): Context {
    return context
}
}