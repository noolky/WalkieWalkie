package com.example.walkiewalkie

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView


class HomeFragment : Fragment() {


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
        val Community:String = "Community Area"
        val CommunityDetails:String = "The community area is a dedicated space where users can interact, engage, and connect with each other. It serves as a virtual community hub where users can share ideas, experiences, opinions, and information related to how to keep our body healthy all the time."
        val btnCommunicationArea = view.findViewById<ImageButton>(R.id.TBcommunication)

        btnCommunicationArea.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_tutorial_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val title = dialog.findViewById<TextView>(R.id.TutorialTitle1)
            val Details = dialog.findViewById<TextView>(R.id.TutorialDetails)
            title.setText(Community)
            Details.setText(CommunityDetails)

            dialog.show()
        }


        val sharedPreferences = context?.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", null)

        return view


    }




}