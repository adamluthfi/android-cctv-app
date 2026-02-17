package com.app.stream.module.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.common.adapter.CameraAdapter
import com.app.stream.common.extension.GridSpacingItemDecoration
import com.app.stream.databinding.ActivityHomeBinding
import com.app.stream.common.extension.startActivitySlideRight
import com.app.stream.module.cctv.CctvActivity
import com.app.stream.remote.model.CameraModel
import com.app.stream.module.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private var isOpen = false
    private var isGrid = false

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

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = R.id.menu_live
    }

    private fun setupListCamera() {
        val spanCount = 2
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)

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
            this@HomeActivity.startActivitySlideRight(
                Intent(applicationContext, CctvActivity::class.java)
            )
        }

        binding.rvCameras.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(
                GridSpacingItemDecoration(spanCount, spacing, true)
            )
            this.adapter = adapter
        }
    }

    fun switchLayout() {
        isGrid = !isGrid
        binding.rvCameras.layoutManager =
            if (isGrid) {
                GridLayoutManager(this, 2)
            } else {
                LinearLayoutManager(this)
            }
    }

    private fun setupButton() {
        binding.toolbar.btnGrid.setOnClickListener {
            switchLayout()
        }
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_live -> {
                    Toast.makeText(this, "Main Live Streaming", Toast.LENGTH_SHORT).show()
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