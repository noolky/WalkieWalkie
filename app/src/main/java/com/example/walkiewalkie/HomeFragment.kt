package com.example.walkiewalkie

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.Profile
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
        val BodyCondition:String = "Body Condition"
        val BodyCondtionDetails:String = "In Body Condition Fragment, we will calculate the BMI and give the suitable advise for User"
        val btnBodyCondition = view.findViewById<ImageButton>(R.id.TBBodycondition)
        val Achievement:String = "Achievement"
        val AchievementDetails:String = "The achievement system with voucher rewards allows users to earn achievements or milestones within a specific platform or application. When users accomplish certain goals, they are rewarded with vouchers, which are typically in the form of QR code."
        val btnAchievement = view.findViewById<ImageButton>(R.id.TBAchievement)
        val Profile:String = "Profile"
        val ProfileDetails:String = "The Profile Page is a user interface component that displays information about a user or account. It allows users to view and edit their profile information, such as updating their own personal data. It has buttons to perform actions like editing the profile, saving changes, or logging out. "
        val btnProfile = view.findViewById<ImageButton>(R.id.TBProfile)

        btnCommunicationArea.setOnClickListener {
            showTutorialDialogBox(Community,CommunityDetails)
        }
        btnBodyCondition.setOnClickListener {
            showTutorialDialogBox(BodyCondition,BodyCondtionDetails)
        }
        btnAchievement.setOnClickListener {
            showTutorialDialogBox(Achievement,AchievementDetails)
        }
        btnProfile.setOnClickListener {
            showTutorialDialogBox(Profile,ProfileDetails)
        }



        val sharedPreferences = context?.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", null)

        return view


    }

    private fun showTutorialDialogBox(Title:String,DetailsNav:String){

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_tutorial_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = dialog.findViewById<TextView>(R.id.TutorialTitle1)
        val Details = dialog.findViewById<TextView>(R.id.TutorialDetails)
        val btnOK:Button = dialog.findViewById(R.id.AllrightButton)
        title.setText(Title)
        Details.setText(DetailsNav)
        btnOK.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }




}