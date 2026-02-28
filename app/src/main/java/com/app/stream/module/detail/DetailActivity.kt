
package com.app.stream.module.detail

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.app.stream.R
import com.app.stream.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(binding.root)
        Toast.makeText(this, Intent().getStringExtra("camera_url").toString(), Toast.LENGTH_SHORT).show()
        setupLiveStream()
        setupButton()

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

    override fun onStop() {
        super.onStop()
        player.release()
    }

    private fun setupLiveStream() {
        playerView = binding.playerPreview
        player = ExoPlayer
            .Builder(this)
            .build()
        playerView.player = player

        val mediaStream = MediaItem.fromUri(Intent().getStringExtra("camera_url").toString())
        player.setMediaItem(mediaStream)
        player.playWhenReady = true
        player.prepare()

    }

    private fun setupButton() {
        binding.btnBack.setOnClickListener {
            this@DetailActivity.finish()
        }
    }
}