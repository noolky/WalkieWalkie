package com.example.walkiewalkie.loginRegister

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R

class ProfileFragment : Fragment() {

    private lateinit var textName: TextView
    private lateinit var textPhone: TextView
    private lateinit var textEmail: TextView
    private lateinit var textPassword: TextView
    private lateinit var helper: Helper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val sharedPreferences = context?.getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", null)
        textName = view.findViewById(R.id.textName)
        textPhone = view.findViewById(R.id.textPhone)
        textEmail = view.findViewById(R.id.textEmail)
        textPassword = view.findViewById(R.id.textPassword)
        helper = Helper(requireContext())

        // Retrieve the user ID from arguments
        val userId = arguments?.getInt("userId", -1) ?: -1

        if (userId != -1) {
            // Get the user data
            val userData = helper.getUserData(userId)

            // Display the user data in the UI
            userData?.let {
                textName.text = it.name
                textPhone.text = it.phone
                textEmail.text = it.email
                textPassword.text = it.password
            }
        }

        return view
    }


}