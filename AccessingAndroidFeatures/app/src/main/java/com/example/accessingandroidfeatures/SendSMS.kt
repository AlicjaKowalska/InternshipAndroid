package com.example.accessingandroidfeatures

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.accessingandroidfeatures.databinding.ActivityMainBinding
import com.example.accessingandroidfeatures.databinding.ActivitySendSmsBinding

class SendSMS : AppCompatActivity() {

    lateinit var sendSMSBinding: ActivitySendSmsBinding

    var userMessage : String = ""
    var userNumber : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendSMSBinding = ActivitySendSmsBinding.inflate(layoutInflater)
        val view = sendSMSBinding.root
        setContentView(view)

        sendSMSBinding.buttonSend.setOnClickListener {

            userMessage = sendSMSBinding.editTextMessage.text.toString()
            userNumber = sendSMSBinding.editTextNumber.text.toString()

            sendSMS(userMessage, userNumber)
        }
    }

    fun sendSMS(userMessage: String, userNumber : String) {

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 1)
        } else {
            val smsManager : SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(userNumber, null, userMessage, null, null)
            Toast.makeText(applicationContext, "Message sent", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val smsManager : SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(userNumber, null, userMessage, null, null)
            Toast.makeText(applicationContext, "Message sent", Toast.LENGTH_LONG).show()
        }
    }
}