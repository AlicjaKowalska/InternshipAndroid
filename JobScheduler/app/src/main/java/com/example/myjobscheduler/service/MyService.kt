package com.example.myjobscheduler.service

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MyService : JobService() {

    companion object {
        const val TAG = "JOB"
        const val VAL = "val"
        const val MACTION = "com.example.myjobscheduler"
        const val STRING_URL = "https://jsonplaceholder.typicode.com/todos/1"
    }

    private var jobCancelled = false

    override fun onStartJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStartJob: " + params.jobId)
        downloadJson(params)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStopJob: " + params.jobId)
        jobCancelled = true
        return true
    }

    private fun downloadJson(parameters: JobParameters) {
        val r = Runnable {
            val jobIntent = Intent(MACTION)
            try {
                val url = URL(STRING_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                val responseCode = connection.responseCode
                if (responseCode != 200)
                    throw RuntimeException("HttpResponseCode: " + responseCode)
                else {
                    val scanner = Scanner(url.openStream())
                    val builder = StringBuilder()
                    while (scanner.hasNext()) {
                        if (jobCancelled) return@Runnable
                        builder.append(scanner.nextLine())
                    }
                    Log.i("JOB", "Downloaded Text: $builder")
                    val jsonObject = JSONObject(builder.toString())
                    Log.i("JOB", "JSON: " + jsonObject.getString("title"))
                    scanner.close()
                    jobIntent.putExtra(VAL, jsonObject.toString())
                    sendBroadcast(jobIntent)
                    jobFinished(parameters, false)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val thread = Thread(r)
        thread.start()
    }

    private fun doBackgroundWork(parameters: JobParameters) {
        val runnable = Runnable {
            val jobIntent = Intent(MACTION)
            for (i in 0..9) {
                Log.i(TAG, "run: $i")
                if (jobCancelled) return@Runnable
                jobIntent.putExtra(VAL, i)
                sendBroadcast(jobIntent)
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.i(TAG, "run: Job done!")
            jobFinished(parameters, false)
        }
        val thread = Thread(runnable)
        thread.start()
    }
}