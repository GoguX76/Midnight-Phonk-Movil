package com.example.midnightphonk

import android.app.Application

class MidnightPhonkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Los repositorios ahora usan API REST y no necesitan inicialización
    }
}
