package com.app.stream.home

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.adapter.CameraAdapter
import com.app.stream.databinding.ActivityHomeBinding
import com.app.stream.model.CameraModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupButton()
        setupListCamera()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
            Toast.makeText(this, "Open ${camera.name}", Toast.LENGTH_SHORT).show()
            // navigate to detail / live stream
        }

        binding.rvCameras.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            this.adapter = adapter
        }
    }

    private fun setupButton() {
        binding.toolbar.btnMenu.setOnClickListener {
            val drawable = binding.toolbar.btnMenu.drawable
            if (drawable is AnimatedVectorDrawable) {
                drawable.start()
                isOpen = !isOpen
            }
        }
    }
}