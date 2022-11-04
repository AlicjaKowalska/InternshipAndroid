package com.example.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class MyListFragment : androidx.fragment.app.ListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = activity?.let {
            ArrayAdapter.createFromResource(it, R.array.cities, android.R.layout.simple_list_item_1) }

        listAdapter = arrayAdapter
        
        listView.setOnItemClickListener { adapterView, view, position, l ->
            val intent = Intent(activity, SecondActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }
}