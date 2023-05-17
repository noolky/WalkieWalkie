package com.example.walkiewalkie.loginRegister

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R
import com.example.walkiewalkie.databinding.FragmentCommunicationAreaBinding
import com.example.walkiewalkie.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var editButton: Button
    private lateinit var saveButton: Button
    private lateinit var db: Helper
    private var isEditable: Boolean = false
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

        editButton = binding.button5
        saveButton = binding.button8

        editButton.setOnClickListener {
            // Toggle the isEditable flag
            isEditable = !isEditable
            updateFieldEditableState()
        }

        saveButton.setOnClickListener{
            if (isEditable){
                //Get the updated profile data from the input fields
                val updateName = binding.textName.text.toString()
                val updatePhone = binding.change1.text.toString()
                val updateEmail = binding.change2.text.toString()
                val updatePassword = binding.change3.text.toString()

                //Update the user profile in the database
                val sharedPreferences = requireContext().getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
                val username = sharedPreferences.getString("username",null)
                db.updateUserProfile(username.toString(), updateName, updatePhone, updateEmail, updatePassword)

                // Toggle the isEditable flag and update the field editable state
                isEditable = !isEditable
                updateFieldEditableState()

                // Show a success message to the user
                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        // Initially set the fields as non-editable
        updateFieldEditableState()

        val logOutButton: ImageButton = view.findViewById(R.id.imageButton6)

        logOutButton.setOnClickListener(){
            // Perform logout actions here (e.g., clear session, delete user data, etc.)
            // Start LoginActivity to go back to the login screen
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Optional: Close the current activity to prevent going back to it using the back button
        }

    }

    private fun updateFieldEditableState() {
        binding.textName.isEnabled = isEditable
        binding.change1.isEnabled = isEditable
        binding.change2.isEnabled = isEditable
        binding.change3.isEnabled = isEditable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}