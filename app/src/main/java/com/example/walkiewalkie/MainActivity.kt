package com.example.walkiewalkie

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.walkiewalkie.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val view = binding.root
        setContentView(view)


        navController=Navigation.findNavController(this,R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation,navController)

    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed() // Proceed with the back button action
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }
}