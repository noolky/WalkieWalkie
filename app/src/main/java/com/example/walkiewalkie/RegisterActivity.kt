package com.example.walkiewalkie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var userNameEditText: EditText
    private lateinit var phoneNumEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var helper: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userNameEditText = findViewById(R.id.editTextTextPersonName)
        phoneNumEditText = findViewById(R.id.textView5)
        emailEditText = findViewById(R.id.editTextTextPersonName4)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword2)
        signUpButton = findViewById(R.id.button3)
        helper = Helper(this)

        signUpButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            val phone = phoneNumEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Fill in all of the details", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            } else {
                val savedata = helper.insertData(username, password, phone, email)

                if (savedata) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}