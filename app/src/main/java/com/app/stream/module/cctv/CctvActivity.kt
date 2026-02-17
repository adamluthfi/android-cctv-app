package com.app.stream.module.cctv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.common.adapter.CameraAdapter
import com.app.stream.common.extension.startActivitySlideRight
import com.app.stream.databinding.ActivityCctvBinding
import com.app.stream.module.detail.DetailActivity
import com.app.stream.remote.model.CameraModel

class CctvActivity : AppCompatActivity() {

    private val binding: ActivityCctvBinding by lazy {
        ActivityCctvBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupButton()
        setupListCamera()

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
            this@CctvActivity.finish()
        }
    }

    private fun setupListCamera() {
        val cameras = listOf(
            CameraModel(
                id = "CAM001",
                name = "Main Entrance",
                location = "Building A - Floor 1",
                isOnline = true,
                isRecording = true,
                previewRes = R.drawable.img_camera_placeholder
            ),
            CameraModel(
                id = "CAM002",
                name = "Parking Area",
                location = "Basement",
                isOnline = false,
                isRecording = false,
                previewRes = R.drawable.img_camera_placeholder
            ),
            CameraModel(
                id = "CAM001",
                name = "Main Entrance",
                location = "Building A - Floor 1",
                isOnline = true,
                isRecording = true,
                previewRes = R.drawable.img_camera_placeholder
            ),
            CameraModel(
                id = "CAM002",
                name = "Parking Area",
                location = "Basement",
                isOnline = false,
                isRecording = false,
                previewRes = R.drawable.img_camera_placeholder
            ),
            CameraModel(
                id = "CAM001",
                name = "Main Entrance",
                location = "Building A - Floor 1",
                isOnline = true,
                isRecording = true,
                previewRes = R.drawable.img_camera_placeholder
            ),
            CameraModel(
                id = "CAM002",
                name = "Parking Area",
                location = "Basement",
                isOnline = false,
                isRecording = false,
                previewRes = R.drawable.img_camera_placeholder
            )
        )

        val adapter = CameraAdapter(cameras) { camera ->
            this@CctvActivity.startActivitySlideRight(Intent(applicationContext, DetailActivity::class.java))
        }

        binding.rvCCTV.apply {
            layoutManager = LinearLayoutManager(this@CctvActivity)
            this.adapter = adapter
        }
    }
}