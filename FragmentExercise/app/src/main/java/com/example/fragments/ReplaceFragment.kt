package com.example.fragments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class ReplaceFragment : AppCompatActivity(){

    lateinit var replace: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_replace)

        replace = findViewById(R.id.buttonReplace)

        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        val firstFragment = FirstFragment()

        fragmentTransaction.add(R.id.frame, firstFragment)
        fragmentTransaction.commit()

        replace.setOnClickListener {
            val secondFragmentManager : FragmentManager = supportFragmentManager
            val secondFragmentTransaction : FragmentTransaction = secondFragmentManager.beginTransaction()
            val secondFragment = SecondFragment()

            secondFragmentTransaction.replace(R.id.frame,secondFragment)
            secondFragmentTransaction.addToBackStack(null)
            secondFragmentTransaction.commit()
        }

    }
}