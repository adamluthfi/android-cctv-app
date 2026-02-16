package com.app.stream.settings

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupButton()
        setupItemSettings()

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
            this@SettingsActivity.finish()
        }
    }

    private fun setupItemSettings() {
        val item1 = findViewById<View>(R.id.editUser)
        item1.findViewById<ImageView>(R.id.icon)
            .setImageResource(R.drawable.ic_settings_user)
        item1.findViewById<TextView>(R.id.title)
            .text = "Users"
        item1.findViewById<TextView>(R.id.subtitle)
            .text = "Edit users"

        val item2 = findViewById<View>(R.id.cameraSetting)
        item2.findViewById<ImageView>(R.id.icon)
            .setImageResource(R.drawable.ic_settings_camera)
        item2.findViewById<TextView>(R.id.title)
            .text = "Cemeras"
        item2.findViewById<TextView>(R.id.subtitle)
            .text = "Camera settings"

        val item3 = findViewById<View>(R.id.tnc)
        item3.findViewById<ImageView>(R.id.icon)
            .setImageResource(R.drawable.ic_settings)
        item3.findViewById<TextView>(R.id.title)
            .text = "Cemeras"
        item3.findViewById<TextView>(R.id.subtitle)
            .text = "Camera settings"
    }
}