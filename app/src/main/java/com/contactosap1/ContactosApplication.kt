package com.contactosap1

import android.app.Application
import com.google.firebase.FirebaseApp

class ContactosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 