package com.example.accessingandroidfeatures

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.accessingandroidfeatures.databinding.ActivityMainBinding
import com.example.accessingandroidfeatures.databinding.ActivitySendEmailBinding
import com.example.accessingandroidfeatures.databinding.ActivitySendSmsBinding

class SendEmail : AppCompatActivity() {

    lateinit var sendEmailBinding: ActivitySendEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendEmailBinding = ActivitySendEmailBinding.inflate(layoutInflater)
        val view = sendEmailBinding.root
        setContentView(view)

        sendEmailBinding.buttonSend.setOnClickListener {
            val userAddress = sendEmailBinding.editTextEmailAddress.text.toString()
            val userSubject = sendEmailBinding.editTextSubject.text.toString()
            val userMessage = sendEmailBinding.editTextMessage.text.toString()

            sendEmail(userAddress, userSubject, userMessage)
        }
    }

    fun sendEmail(userAddress : String, userSubject : String, userMessage : String) {

        val emailAddresses = arrayOf(userAddress)

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto: ")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddresses)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, userSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, userMessage)

        if ( emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Choose an app"))
        }
    }
}