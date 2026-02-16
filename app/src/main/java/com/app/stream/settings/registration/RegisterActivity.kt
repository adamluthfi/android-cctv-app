package com.app.stream.settings.registration

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.databinding.ActivityRegisterBinding
import com.app.stream.extension.startActivitySlideRight
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.getValue

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
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
            this@RegisterActivity.finish()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.tietUserId.text?.isEmpty() == true) {
                binding.tiluserID.error = "User ID is required"
                binding.tiluserID.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietfirstname.text?.isEmpty() == true) {
                binding.tilfirstname.error = "First name is required"
                binding.tilfirstname.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietlastname.text?.isEmpty() == true) {
                binding.tillastname.error = "Last name is required"
                binding.tillastname.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tiePassword.text?.isEmpty() == true) {
                binding.tilPassword.error = "Password is required"
                binding.tilPassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietrepassword.text?.isEmpty() == true) {
                binding.tilrepassword.error = "Confirm password is required"
                binding.tilrepassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietrepassword.text != binding.tiePassword.text) {
                binding.tilrepassword.error = "Password does not match"
                binding.tilrepassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else {
                binding.tiluserID.error = null
                binding.tilfirstname.error = null
                binding.tillastname.error = null
                binding.tilPassword.error = null
                binding.tilrepassword.error = null
            }
        }
    }

    private fun setupEditText() {
        setupTextInput(binding.tiluserID, binding.tietUserId)
        setupTextInput(binding.tilfirstname, binding.tietfirstname)
        setupTextInput(binding.tillastname, binding.tietlastname)
        setupTextInput(binding.tilPassword, binding.tiePassword)
        setupTextInput(binding.tilrepassword, binding.tietrepassword)
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