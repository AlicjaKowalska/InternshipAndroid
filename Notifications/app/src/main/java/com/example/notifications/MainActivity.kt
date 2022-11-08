package com.example.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifications.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "1"

    lateinit var mainBinding : ActivityMainBinding
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonSendNotification.setOnClickListener {
            counter++
            mainBinding.buttonSendNotification.text = counter.toString()
            if(counter % 5 == 0) {
                startNotification()
            }
        }

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        mainBinding.buttonPeriodicNotification.setOnClickListener {

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Set Notification Time")
                .build()

            timePicker.show(supportFragmentManager, "1")

            timePicker.addOnPositiveButtonClickListener {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                val intent = Intent(applicationContext, NotificationReceiver::class.java)

                val pendingIntent = if (Build.VERSION.SDK_INT >= 23) {
                    PendingIntent.getBroadcast(applicationContext
                    ,100
                    ,intent
                    ,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                } else {
                    PendingIntent.getBroadcast(applicationContext
                        ,100
                        ,intent
                        ,PendingIntent.FLAG_UPDATE_CURRENT)
                }

                val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                    ,calendar.timeInMillis
                    ,AlarmManager.INTERVAL_DAY
                    ,pendingIntent)

                Toast.makeText(applicationContext, "The alarm has been set", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun startNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT)

            val manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)

            builder.setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Title")
                .setContentText("Notification Text")

        } else {
            builder.setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Notification Title")
                .setContentText("This is the notification text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        notificationManagerCompat.notify(1, builder.build())
    }
}