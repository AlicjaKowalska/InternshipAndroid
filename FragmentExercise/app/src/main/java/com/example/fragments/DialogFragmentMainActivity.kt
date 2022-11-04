package com.example.fragments

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class DialogFragmentMainActivity : AppCompatActivity() {

    lateinit var show : Button
    lateinit var name : TextView
    lateinit var age : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogfragment_main)

        show = findViewById(R.id.buttonShow)
        name = findViewById(R.id.textViewName)
        age = findViewById(R.id.textViewAge)

        show.setOnClickListener {
            val fragmentManager : FragmentManager = supportFragmentManager
            val myDialogFragment = MyDialogFragment()

            myDialogFragment.isCancelable = false

            myDialogFragment.show(fragmentManager, "MyDialogFragment")
        }
    }

    fun getUserData(userName : String, userAge : Int) {
        name.text = "Name: $userName"
        age.text = "Age: $userAge"
    }
}