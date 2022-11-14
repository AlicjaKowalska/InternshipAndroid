package com.example.services

import android.app.Activity
import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : IntentService("MyIntentService") {

    private var result = Activity.RESULT_CANCELED

    companion object {
        const val NOTIFICATION = "com.example.services.MyService"
        const val TAG = "Running service"
        const val KEY_NUM = "key_num"
        const val RESULT = "result"
        const val SUM = "sum"
    }

    fun sum(a: Int): Int {
        return a + a
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?) {
        val a = intent!!.getIntExtra(KEY_NUM, 1)
        val sum = sum(a)
        Log.i(TAG, "onStartCommand: " + sum(a))
        result = Activity.RESULT_OK

        publishSum(sum, result)
    }

    private fun publishSum(sum: Int, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(SUM, sum)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }
}