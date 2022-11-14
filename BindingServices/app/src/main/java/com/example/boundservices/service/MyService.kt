package com.example.boundservices.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    val TAG = "ServiceRunning"

    private val binder: Binder = ServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind: ")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    inner class ServiceBinder : Binder() {
        fun getService() : MyService = this@MyService
    }

    fun serveValue(): String {
        return "From the service!"
    }
}