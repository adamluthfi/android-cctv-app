package com.app.stream.module.settings.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.databinding.ActivityPasswordBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PasswordActivity : AppCompatActivity() {
    private val binding: ActivityPasswordBinding by lazy {
        ActivityPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupButton()
        setupEditText()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }

    private fun setupButton() {
        binding.toolbar.setNavigationOnClickListener {
            this@PasswordActivity.finish()
        }

        binding.btnChangePassword.setOnClickListener {
            if (binding.tietCurPassword.text?.isEmpty() == true) {
                binding.tilCurPassword.error = "Current password is required"
                binding.tilCurPassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietNewPassword.text?.isEmpty() == true) {
                binding.tilNewPassword.error = "New password is required"
                binding.tilNewPassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietRePassword.text?.isEmpty() == true) {
                binding.tilRePassword.error = "Confirm password is required"
                binding.tilRePassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietRePassword.text.toString() != binding.tietNewPassword.text.toString()) {
                binding.tilRePassword.error = "Password does not match"
                binding.tilRePassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else {
                binding.tilCurPassword.error = null
                binding.tilNewPassword.error = null
                binding.tilRePassword.error = null
                this@PasswordActivity.finish()
            }
        }
    }

    private fun setupEditText() {
        setupTextInput(binding.tilCurPassword, binding.tietCurPassword)
        setupTextInput(binding.tilNewPassword, binding.tietNewPassword)
        setupTextInput(binding.tilRePassword, binding.tietRePassword)
    }

    private fun setupTextInput(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText) {
        textInputEditText.addTextChangedListener(object : TextWatcher {
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
                textInputLayout.boxStrokeColor = getColor(R.color.purple_500)
                textInputLayout.error = null
            }
        })
    }
}