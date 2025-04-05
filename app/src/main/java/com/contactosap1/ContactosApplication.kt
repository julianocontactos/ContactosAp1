package com.contactosap1

import android.app.Application
import com.contactosap1.utils.DefaultData
import com.google.firebase.FirebaseApp

class ContactosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        DefaultData.criarDadosPadrao()
    }
} 