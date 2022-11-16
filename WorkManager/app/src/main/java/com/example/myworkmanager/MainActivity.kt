package com.example.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.myworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.button.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            //Passing data to worker
            val data = Data.Builder()
                .putString(MyWorker.DATA_KEY, "Data from Activity")
                .build()

            //periodic work request
            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                MyWorker::class.java,
                10,
                TimeUnit.HOURS
            )
                .build()
            val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data) //passing data to worker from activity
                .build()

            //Chaining works
            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    "Unique", ExistingPeriodicWorkPolicy.KEEP,
                    periodicWorkRequest
                )
            //.beginWith(workRequest)
            //.then(workRequest)
            //.then(workRequest)
            //.enqueue();
            WorkManager.getInstance(applicationContext).enqueue(workRequest)
            WorkManager.getInstance(applicationContext)
                .getWorkInfoByIdLiveData(workRequest.id)
                .observe(
                    this@MainActivity
                ) { workInfo ->
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val result = workInfo
                            .outputData.getString(MyWorker.DATA_KEY)
                        mainBinding.textView.text = result
                    }
                }
        }
    }

    private fun cancelWork(workRequest: WorkRequest) {
        WorkManager.getInstance(applicationContext)
            .cancelWorkById(workRequest.id)
    }
}