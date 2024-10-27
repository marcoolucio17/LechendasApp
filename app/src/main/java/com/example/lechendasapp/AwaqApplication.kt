package com.example.lechendasapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AwaqApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}