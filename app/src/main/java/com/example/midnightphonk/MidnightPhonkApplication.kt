package com.example.midnightphonk

import android.app.Application
import data.ProductRepository
import data.UserRepository

class MidnightPhonkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProductRepository.initialize(this)
        UserRepository.initialize(this)
    }
}