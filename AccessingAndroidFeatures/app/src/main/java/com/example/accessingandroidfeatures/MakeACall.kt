package com.example.accessingandroidfeatures

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.accessingandroidfeatures.databinding.ActivityMainBinding
import com.example.accessingandroidfeatures.databinding.ActivityMakeAcallBinding

class MakeACall : AppCompatActivity() {

    lateinit var makeACallBinding: ActivityMakeAcallBinding

    var userNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeACallBinding = ActivityMakeAcallBinding.inflate(layoutInflater)
        val view = makeACallBinding.root
        setContentView(view)

        makeACallBinding.buttonCall.setOnClickListener {

            userNumber = makeACallBinding.editTextPhone.text.toString()
            startCall(userNumber)
        }
    }

    fun startCall(userNumber : String) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 100)
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$userNumber")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$userNumber")
            startActivity(intent)
        }
    }
}