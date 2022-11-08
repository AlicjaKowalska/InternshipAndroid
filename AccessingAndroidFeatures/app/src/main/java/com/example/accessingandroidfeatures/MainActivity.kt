package com.example.accessingandroidfeatures

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Sms
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.accessingandroidfeatures.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonSendSMS.setOnClickListener {
            val intent = Intent(this, SendSMS::class.java)
            startActivity(intent)
        }

        mainBinding.buttonSendEmail.setOnClickListener {
            val intent = Intent(this, SendEmail::class.java)
            startActivity(intent)
        }

        mainBinding.buttonCall.setOnClickListener {
            val intent = Intent(this, MakeACall::class.java)
            startActivity(intent)
        }

        mainBinding.buttonSpeechToText.setOnClickListener {
            val intent = Intent(this, SpeechToText::class.java)
            startActivity(intent)
        }
    }
}