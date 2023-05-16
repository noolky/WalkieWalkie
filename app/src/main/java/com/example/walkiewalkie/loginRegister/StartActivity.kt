package com.example.walkiewalkie.loginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.walkiewalkie.R

class StartActivity : AppCompatActivity() {
    private lateinit var btnssignup: Button
    private lateinit var btnslogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        btnssignup = findViewById(R.id.registerbtn)
        btnslogin = findViewById(R.id.loginbtn)

        btnssignup.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnslogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}