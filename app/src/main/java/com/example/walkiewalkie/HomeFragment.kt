package com.example.walkiewalkie

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class HomeFragment : Fragment() {

    lateinit var Name: TextView
    private lateinit var buttonToStepCount: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the button by its ID
        val buttonToStepCount: Button = view.findViewById(R.id.buttonToStepCount)

        // Set a click listener to navigate to the MainActivity
        buttonToStepCount.setOnClickListener {
            val intent = Intent(requireActivity(), com.example.walkiewalkie.AchievementStepActivity.MainActivity::class.java)
            startActivity(intent)
        }
        Name = view.findViewById(R.id.textView) // Replace `textViewName` with the actual ID of your TextView

        val sharedPreferences = context?.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", null)
        Name.text = username
        return view


    }




}