package com.example.myjobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myjobscheduler.databinding.ActivityMainBinding
import com.example.myjobscheduler.service.MyService
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        const val JOB_ID = 101
    }

    lateinit var mainBinding: ActivityMainBinding

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                try {
                    val res = JSONObject(bundle.getString(MyService.VAL))
                    mainBinding.textView.text = res.getString("title")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(MyService.MACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonStart.setOnClickListener{
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder( JOB_ID,
                ComponentName(applicationContext, MyService::class.java))
                .setMinimumLatency(0)
                .build()
            jobScheduler.schedule(jobInfo)
        }

        mainBinding.buttonCancel.setOnClickListener{
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
            Log.i("JOB", "run: Job canceled")
        }
    }
}