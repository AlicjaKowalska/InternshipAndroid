package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val position = intent.getIntExtra("position", 0)

        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        val secondFragment = SecondFragmentList()

        val bundle = Bundle()
        bundle.putInt("position", position)
        secondFragment.arguments = bundle

        fragmentTransaction.add(R.id.FrameLayout, secondFragment)

        fragmentTransaction.commit()
    }
}