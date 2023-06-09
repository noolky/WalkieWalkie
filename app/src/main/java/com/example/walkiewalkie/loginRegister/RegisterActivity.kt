package com.example.walkiewalkie.loginRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R

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

            // Close the keyboard before performing the registration actions
            closeKeyboard()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Fill in all of the details", Toast.LENGTH_SHORT).show()
            } else if (!isValidPhoneNumber(phone)){
                Toast.makeText(this, "Invalid Phone Number, MUST CONTAIN 10 NUMBER", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)){
                Toast.makeText(this,"Invalid Email Format, MUST CONTAIN @ SYMBOL", Toast.LENGTH_SHORT).show()
            } else if (password.length <= 6){
                Toast.makeText(this, "Password should be at least 6 Characters long", Toast.LENGTH_SHORT).show()
            } else if(password != confirmPassword) {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            } else{
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

    private fun isValidEmail( email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.contains("@")
    }

    private fun isValidPhoneNumber ( phoneNumber: String): Boolean{
        return phoneNumber.matches(Regex("\\d{10}"))
    }

    private fun closeKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}