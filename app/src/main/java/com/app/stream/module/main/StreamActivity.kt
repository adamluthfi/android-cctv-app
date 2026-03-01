package com.app.stream.module.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.common.extension.hideLoading
import com.app.stream.common.extension.showLoading
import com.app.stream.databinding.ActivityStreamBinding
import com.app.stream.module.home.HomeActivity
import com.app.stream.remote.ApiResult
import com.app.stream.module.main.viewmodel.StreamViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StreamActivity : AppCompatActivity() {

    private val binding: ActivityStreamBinding by lazy {
        ActivityStreamBinding.inflate(layoutInflater)
    }

    private val viewmodel = StreamViewModel()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        configButton()
        setupEditText()
        sessionManager = SessionManager(this)

        viewmodel
            .loginState
            .observe(this@StreamActivity) {
                when (it) {
                    is ApiResult.Loading -> {
                        binding.btnSignIn.showLoading()
                        binding.btnSignIn.animate()
                            .alpha(0.85f)
                            .setDuration(150)
                            .start()
                    }
                    is ApiResult.Success -> {
                        lifecycleScope.launch {
                            delay(200)
                            binding.btnSignIn.hideLoading("SIGN IN")
                            this@StreamActivity.startActivity(
                                Intent(
                                    applicationContext,
                                    HomeActivity::class.java
                                )
                            )
                            this@StreamActivity.finish()
                            sessionManager.saveToken(it.data.data?.accessToken.toString(), "")
                        }
                    }
                    is ApiResult.Error -> {
                        binding.btnSignIn.hideLoading("SIGN IN")
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onDestroy() {
        super.onDestroy()
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
                viewmodel.login(binding.tietUserId.text.toString(), binding.tiePassword.text.toString())
            }
        }
    }

    private fun setupEditText() {
        setupTextInput(binding.tiluserID, binding.tietUserId)
        setupTextInput(binding.tilPassword, binding.tiePassword)
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