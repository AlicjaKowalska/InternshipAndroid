package com.example.boundservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.boundservices.databinding.ActivityMainBinding
import com.example.boundservices.service.MyService

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    val TAG = "ServiceRunning"

    private lateinit var mService: MyService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MyService.ServiceBinder
            mService = binder.getService()
            mBound = true
            Log.i(TAG, "onServiceConnected: ")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBound = false
            Log.i(TAG, "onServiceDisconnected: ")
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
        Log.i(TAG, "onStop: Service Unbound")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.textView.setOnClickListener {
            if (mBound) {
                mainBinding.textView.text = mService.serveValue()
            }
        }
    }
}