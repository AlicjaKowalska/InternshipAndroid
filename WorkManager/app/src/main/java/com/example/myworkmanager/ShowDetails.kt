package com.example.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myworkmanager.databinding.ActivityShowDetailsBinding

class ShowDetails : AppCompatActivity() {

    lateinit var showDetailsBinding: ActivityShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailsBinding = ActivityShowDetailsBinding.inflate(layoutInflater)
        val view = showDetailsBinding.root
        setContentView(view)
    }
}