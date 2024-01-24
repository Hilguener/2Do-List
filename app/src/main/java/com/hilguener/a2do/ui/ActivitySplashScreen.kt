package com.hilguener.a2do.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hilguener.a2do.databinding.ActivitySplashScreenBinding

class ActivitySplashScreen : AppCompatActivity() {
    private var handler= Handler()
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}