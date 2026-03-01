package com.app.stream.module.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.common.adapter.LocationAdapter
import com.app.stream.databinding.ActivityHomeBinding
import com.app.stream.common.extension.startActivitySlideRight
import com.app.stream.module.cctv.CctvActivity
import com.app.stream.module.home.viewmodel.HomeViewModel
import com.app.stream.module.settings.SettingsActivity
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.ChannelCameraResponse
import com.app.stream.ui.common.loading.LoadingController
import com.app.stream.ui.common.loading.LoadingManager

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewmodel = HomeViewModel()
    private lateinit var loadingController: LoadingController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        loadingController = LoadingManager(supportFragmentManager)
        setupButton()
        viewmodel.channels(SessionManager(this).getAccessToken().toString())
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = R.id.menu_live
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingController.hide()
    }

    private fun setupListCamera(channels: List<ChannelCameraResponse>?) {

        val adapter = LocationAdapter(channels) { camera ->
            this@HomeActivity.startActivitySlideRight(
                Intent(applicationContext, CctvActivity::class.java)
                    .putExtra("channel_id", camera.id)
            )
        }

        binding.rvCameras.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            this.adapter = adapter
        }
    }

    private fun fetchCamera() {
        viewmodel.homeState
            .observe(this@HomeActivity) {
                when (it) {
                    is ApiResult.Loading -> {
                        loadingController.show("Loading...")
                    }
                    is ApiResult.Success -> {
                        loadingController.hide()
                        setupListCamera(it.data.data)
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
    private fun setupButton() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_live -> {
                    fetchCamera()
                    true
                }

                R.id.menu_settings -> {
                    this@HomeActivity.startActivitySlideRight(
                        Intent(
                            applicationContext,
                            SettingsActivity::class.java
                        )
                    )
                    true
                }
                else -> false
            }
        }
    }
}