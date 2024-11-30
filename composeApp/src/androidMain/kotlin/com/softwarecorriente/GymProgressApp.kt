package com.softwarecorriente

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class GymProgressApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@GymProgressApp)
        }
    }
}