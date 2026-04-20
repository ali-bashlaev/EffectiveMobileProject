package com.example.effectivemobileproject

import android.app.Application
import com.example.effectivemobileproject.di.appModule
import com.example.data.di.dataModule // Required import
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule, dataModule)
        }
    }
}