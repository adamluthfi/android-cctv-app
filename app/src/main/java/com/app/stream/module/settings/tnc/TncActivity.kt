package com.app.stream.module.settings.tnc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.databinding.ActivitySettingsBinding
import com.app.stream.databinding.ActivityTncBinding

class TncActivity : AppCompatActivity() {

    private val binding: ActivityTncBinding by lazy {
        ActivityTncBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupButton()
        binding.webView.loadUrl("file:///android_asset/terms_conditions.html")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setupButton() {
        binding.toolbar.setNavigationOnClickListener {
            this@TncActivity.finish()
        }
    }
}