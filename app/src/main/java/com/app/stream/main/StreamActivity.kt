package com.app.stream.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.databinding.ActivityStreamBinding
import com.app.stream.home.HomeActivity

class StreamActivity : AppCompatActivity() {

    private val binding: ActivityStreamBinding by lazy {
        ActivityStreamBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        configButton()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



    }

    private fun configButton() {
        binding.btnSignIn.setOnClickListener {
            this@StreamActivity.startActivity(Intent(applicationContext, HomeActivity::class.java))
            this@StreamActivity.finish()
        }
    }
}