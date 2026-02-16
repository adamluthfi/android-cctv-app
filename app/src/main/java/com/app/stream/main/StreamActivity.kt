package com.app.stream.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        setupTextInput()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configButton() {
        binding.btnSignIn.setOnClickListener {
            if (binding.tietUserId.text?.isEmpty() == true) {
                binding.tiluserID.error = "User ID is required"
                binding.tiluserID.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tiePassword .text?.isEmpty() == true) {
                binding.tilPassword.error = "Password is required"
                binding.tilPassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else {
                binding.tiluserID.error = null
                binding.tilPassword.error = null
                this@StreamActivity.startActivity(Intent(applicationContext, HomeActivity::class.java))
                this@StreamActivity.finish()
            }
        }
    }

    private fun setupTextInput() {
        binding.tietUserId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                binding.tiluserID.boxStrokeColor = getColor(R.color.purple_500)
                binding.tiluserID.error = null
            }
        })

        binding.tiePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                binding.tilPassword.boxStrokeColor = getColor(R.color.purple_500)
                binding.tilPassword.error = null
            }
        })
    }
}