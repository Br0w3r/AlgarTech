// TechApplication.kt
package com.eishoncorp.algartech

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TechApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
