package com.app.stream.module.settings

import android.content.Intent
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
import com.app.stream.common.extension.startActivitySlideRight
import com.app.stream.module.main.StreamActivity
import com.app.stream.module.settings.password.PasswordActivity
import com.app.stream.module.settings.registration.RegisterActivity
import kotlin.jvm.java

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

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this@SettingsActivity,StreamActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this@SettingsActivity.startActivity(intent)
            this@SettingsActivity.finish()
        }

        binding.editUser.mainItemSetting.setOnClickListener {
            this@SettingsActivity.startActivitySlideRight(Intent(this@SettingsActivity,
                RegisterActivity::class.java))
        }

        binding.changePassword.mainItemSetting.setOnClickListener {
            this@SettingsActivity.startActivitySlideRight(Intent(this@SettingsActivity,
                PasswordActivity::class.java))
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

        val item2 = findViewById<View>(R.id.changePassword)
        item2.findViewById<ImageView>(R.id.icon)
            .setImageResource(R.drawable.ic_password)
        item2.findViewById<TextView>(R.id.title)
            .text = "Password"
        item2.findViewById<TextView>(R.id.subtitle)
            .text = "Change password"

        val item3 = findViewById<View>(R.id.tnc)
        item3.findViewById<ImageView>(R.id.icon)
            .setImageResource(R.drawable.ic_settings)
        item3.findViewById<TextView>(R.id.title)
            .text = "Settings"
        item3.findViewById<TextView>(R.id.subtitle)
            .text = "Term & Condition"
    }
}