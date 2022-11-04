package com.example.fragments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView.FindListener
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    lateinit var replaceFragment : Button
    lateinit var listFragment : Button
    lateinit var dialogFragment : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment = findViewById(R.id.buttonReplaceFragment)
        listFragment = findViewById(R.id.buttonListFragment)
        dialogFragment = findViewById(R.id.buttonDialogFragment)

        replaceFragment.setOnClickListener {
            var intent = Intent(this@MainActivity, ReplaceFragment::class.java)
            startActivity(intent)
        }

        listFragment.setOnClickListener {
            var intent = Intent(this@MainActivity, ListFragment::class.java)
            startActivity(intent)
        }

        dialogFragment.setOnClickListener {
            var intent = Intent(this@MainActivity, DialogFragmentMainActivity::class.java)
            startActivity(intent)
        }

    }
}