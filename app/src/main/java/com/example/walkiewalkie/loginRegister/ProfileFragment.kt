package com.example.walkiewalkie.loginRegister

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R
import com.example.walkiewalkie.databinding.FragmentCommunicationAreaBinding
import com.example.walkiewalkie.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var textName: TextView
    private lateinit var textPhone: TextView
    private lateinit var textEmail: TextView
    private lateinit var textPassword: TextView
    private lateinit var db: Helper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Helper(requireContext())

        val sharedPreferences = requireContext().getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val dataDetails = db.getUserDetails(username.toString())
        // Update your UI with the retrieved user details

        binding.textName.setText(dataDetails?.username.toString())
        binding.change1.setText(dataDetails?.phone.toString())
        binding.change2.setText(dataDetails?.email.toString())
        binding.change3.setText(dataDetails?.password.toString())
    }


}