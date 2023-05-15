package com.example.walkiewalkie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
//import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var editUser: EditText
    private lateinit var editPassword: EditText
    private lateinit var help: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.button4)
        editUser = findViewById(R.id.editTextTextPersonName2)
        editPassword = findViewById(R.id.editTextTextPassword3)
        help = Helper(this)

        loginButton.setOnClickListener{

            val user = editUser.text.toString()
            val password = editPassword.text.toString()

            if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "Fill Username & Password", Toast.LENGTH_SHORT).show()
            }
            else{
                val checkUser = help.checkUserPass(user, password)
                if (checkUser==true){
                    Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
//                    // Launch the ProfileFragment
//                    val fragmentTransaction = supportFragmentManager.beginTransaction()
//                    fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
//                    fragmentTransaction.commit()
                    val intent = Intent(applicationContext, Success::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}