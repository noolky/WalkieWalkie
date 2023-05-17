package com.example.walkiewalkie.AchievementStepActivity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setupActionBar()
    }



    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        init()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        init()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        init()
    }

    private fun init() {
        initData()
        initListener()
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initListener()

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Step Counter" // Set the title
            setDisplayHomeAsUpEnabled(true) // Enable the back button
        }
    }

}
/*
val activity = requireActivity() as AppCompatActivity
activity.supportActionBar?.apply {
    setTitle("Step Counting") // Set the title
    setDisplayHomeAsUpEnabled(true) // Enable the back button
}*/
