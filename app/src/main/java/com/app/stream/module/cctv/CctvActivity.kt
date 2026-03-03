package com.app.stream.module.cctv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    private var url: String? = null
    private var nameCamera: String? = null


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
            intent.getLongExtra("channel_id",0)
        )
        fetchCamera()
        setupFullScreen()

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

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onResume() {
        super.onResume()
        fetchCamera()
        player.playWhenReady = true
        player.prepare()
    }

    private fun fetchCamera() {
        viewmodel.cctvState
            .observe(this@CctvActivity) {
                when (it) {
                    is ApiResult.Loading -> {}
                    is ApiResult.Success -> {
                        it.data.data
                        setupListCamera(it.data.data)
                        url = it.data.data?.first()?.url
                        nameCamera = it.data.data?.first()?.name
                        url?.let { it1 -> setupLiveStream(it1) }
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupListCamera(cameras: List<Camera>?) {

        val adapter = CameraAdapter(cameras) { camera ->
            camera.url?.let {
                setupLiveStream(it)
                url = it
            }
            camera.name.let {
                nameCamera = it
            }

        }

        binding.rvCCTV.apply {
            layoutManager = LinearLayoutManager(this@CctvActivity)
            this.adapter = adapter
        }
    }

    private fun setupFullScreen() {
        binding.btnFullScreen.setOnClickListener {
            this@CctvActivity.startActivitySlideRight(
                Intent(this@CctvActivity, DetailActivity::class.java)
                    .putExtra("url", url)
                    .putExtra("name_camera", nameCamera))
        }
    }

    private fun setupLiveStream(url: String) {
        playerView = binding.playerPreview
        player = ExoPlayer
            .Builder(this)
            .build()
        playerView.player = player

        val mediaStream = MediaItem.fromUri(url)
        player.setMediaItem(mediaStream)
        player.playWhenReady = true
        player.prepare()

    }
}