package com.example.services

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.progressBar.setVisibility(View.INVISIBLE)

        mainBinding.button.setOnClickListener {
            DownloadTask(
                applicationContext
            ).execute(10)
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
            mainBinding.textView.text = s
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            mainBinding.progressBar.progress = values.get(0)!!
            mainBinding.textView.text = String.format("Running " + values.get(0))
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