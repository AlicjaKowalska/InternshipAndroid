package com.example.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var count = 1

    private val receiver : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                val sum = bundle.getInt(MyService.SUM)
                val resultCode = bundle.getInt(MyService.RESULT)
                if (resultCode == RESULT_OK) {
                    mainBinding.textViewBroadcast.text = "Process complete with sum $sum"
                } else {
                    mainBinding.textViewBroadcast.text = "Process failed!"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(MyService.NOTIFICATION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.progressBar.setVisibility(View.INVISIBLE)

        mainBinding.buttonAsyncTask.setOnClickListener {
            DownloadTask(
                applicationContext
            ).execute(10)
        }

        mainBinding.textViewAsyncTask.setOnClickListener {
            val intent = Intent(applicationContext, MyService::class.java)
            intent.putExtra(MyService.KEY_NUM, 90)
            startService(intent)
        }

        mainBinding.buttonBroadcast.setOnClickListener {
            val intent = Intent(applicationContext, MyService::class.java)
            intent.putExtra(MyService.KEY_NUM, 90)
            startService(intent)
        }
    }

    inner class DownloadTask(applicationContext: Context?) : AsyncTask<Int?, Int?, String?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            count = 1
            mainBinding.progressBar.visibility = View.VISIBLE
            mainBinding.progressBar.progress = 0
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            mainBinding.progressBar.visibility = View.GONE
            mainBinding.textViewAsyncTask.text = s
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            mainBinding.progressBar.progress = values.get(0)!!
            mainBinding.textViewAsyncTask.text = String.format("Running " + values.get(0))
        }

        override fun doInBackground(vararg integers: Int?): String? {
            while (count <= integers[0]!!) {
                try {
                    Thread.sleep(1000)
                    publishProgress(count)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                count++
            }
            return "Task Completed"
        }
    }
}