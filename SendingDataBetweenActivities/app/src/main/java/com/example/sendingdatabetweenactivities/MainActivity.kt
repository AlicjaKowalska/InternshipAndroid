package com.example.sendingdatabetweenactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    lateinit var etName : TextInputEditText
    lateinit var etEmail : TextInputEditText
    lateinit var etPhone : TextInputEditText
    lateinit var ataButton : Button
    lateinit var atfButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.editTextName)
        etEmail = findViewById(R.id.editTextEmail)
        etPhone = findViewById(R.id.editTextPhone)
        ataButton = findViewById(R.id.buttonActivityToActivity)
        atfButton = findViewById(R.id.buttonActivityToFragment)

        val fm : FragmentManager = supportFragmentManager
        val ft : FragmentTransaction = fm.beginTransaction()
        val firstFragment = FirstFragment()

        ataButton.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString().toLong()

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("phone", phone)
            startActivity(intent)
        }

        atfButton.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString().toLong()

            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("email", email)
            bundle.putLong("phone", phone)

            firstFragment.arguments = bundle
            ft.add(R.id.frame, firstFragment)
            ft.commit()
        }
    }
}