package com.example.icerockrepoapp

import android.app.Application
import com.example.icerockrepoapp.model.repository.AppRepository

class App: Application() {
    lateinit var appRepository: AppRepository

    override fun onCreate() {
        super.onCreate()
        appRepository = AppRepository(applicationContext)
    }
}