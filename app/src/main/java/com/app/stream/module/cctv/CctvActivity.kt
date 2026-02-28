package com.app.stream.module.cctv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.common.adapter.CameraAdapter
import com.app.stream.common.extension.startActivitySlideRight
import com.app.stream.databinding.ActivityCctvBinding
import com.app.stream.module.cctv.viewmodel.CctvViewModel
import com.app.stream.module.detail.DetailActivity
import com.app.stream.module.home.viewmodel.HomeViewModel
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.Camera
import com.app.stream.remote.model.CameraResponse

class CctvActivity : AppCompatActivity() {

    private val binding: ActivityCctvBinding by lazy {
        ActivityCctvBinding.inflate(layoutInflater)
    }

    private val viewmodel = CctvViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupButton()
        viewmodel.cameras(
            SessionManager(this).getAccessToken().toString(),
            Intent().getLongExtra("channel_id",1)
        )
        fetchCamera()

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

    private fun fetchCamera() {
        viewmodel.cctvState
            .observe(this@CctvActivity) {
                when (it) {
                    is ApiResult.Loading -> {}
                    is ApiResult.Success -> {
                        it.data.data
                        setupListCamera(it.data.data)
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupListCamera(cameras: List<Camera>?) {

        val adapter = CameraAdapter(cameras) { camera ->
            this@CctvActivity
                .startActivitySlideRight(
                    Intent(applicationContext,
                        DetailActivity::class.java)
                        .putExtra("camera_url",camera.url)
                )
        }

        binding.rvCCTV.apply {
            layoutManager = LinearLayoutManager(this@CctvActivity)
            this.adapter = adapter
        }
    }
}