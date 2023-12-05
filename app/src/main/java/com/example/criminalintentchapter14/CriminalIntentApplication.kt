package com.example.criminalintentchapter14

import android.app.Application

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this.applicationContext)
    }
}