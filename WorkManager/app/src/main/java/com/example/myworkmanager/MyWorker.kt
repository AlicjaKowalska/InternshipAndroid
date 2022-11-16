package com.example.myworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "Work"
        const val DATA_KEY = "data_key"
        const val MESSAGE_CHANNEL = "message_channel"
        const val TASK_NOTIFICATION = "Task Notification"
        const val MESSAGE_ID = 1001
    }

    override fun doWork(): Result {
        Log.i(TAG, "Doing great work!")
        //Passing data to activity
        val data = Data.Builder()
            .putString(DATA_KEY, "Hello From doneWork!")
            .build()

        //Receiving data from activity
        val response = inputData.getString(DATA_KEY)
        Log.i(TAG, "Received: $response")
        displayNotification(response, data.getString(DATA_KEY))
        return Result.success(data)
    }

    private fun displayNotification(title: String?, message: String?) {
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MESSAGE_CHANNEL,
                TASK_NOTIFICATION, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationIntent = Intent(applicationContext, ShowDetails::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(
            applicationContext,
            MESSAGE_CHANNEL
        ).setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.star_on)
            .setContentIntent(pendingIntent)
            .setColor(ContextCompat.getColor(applicationContext, R.color.teal_200))
            .setAutoCancel(true)
        notificationManager.notify(MESSAGE_ID, notification.build())
    }
}