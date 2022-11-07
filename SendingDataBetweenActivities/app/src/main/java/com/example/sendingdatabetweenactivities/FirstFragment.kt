package com.example.sendingdatabetweenactivities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FirstFragment : Fragment() {

    lateinit var name : TextView
    lateinit var email : TextView
    lateinit var phone : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_first, container, false)

        name = view.findViewById(R.id.textViewName)
        email = view.findViewById(R.id.textViewName)
        phone = view.findViewById(R.id.textViewPhone)

        name.text = arguments?.getString("name")
        email.text = arguments?.getString("email")
        phone.text = arguments?.getLong("phone").toString()

        // Inflate the layout for this fragment
        return view
    }
}