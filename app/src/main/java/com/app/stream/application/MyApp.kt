package com.app.stream.application

import android.app.Application
import com.app.stream.common.SessionManager
import com.app.stream.remote.NetworkModule

class MyApp: Application() {

    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(applicationContext)
    }
}