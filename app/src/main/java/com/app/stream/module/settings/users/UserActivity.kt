package com.app.stream.module.settings.users

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.common.adapter.CameraAdapter
import com.app.stream.common.adapter.UsersAdapter
import com.app.stream.databinding.ActivityUserBinding
import com.app.stream.module.settings.registration.viewmodel.RegistrationViewModel
import com.app.stream.module.settings.users.viewmodel.UserViewModel
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.Camera
import com.app.stream.remote.model.UserListResponse
import com.app.stream.ui.common.loading.LoadingController
import com.app.stream.ui.common.loading.LoadingManager
import kotlin.getValue

class UserActivity : AppCompatActivity() {

    private val binding: ActivityUserBinding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    private val viewmodel = UserViewModel()
    private lateinit var loadingController: LoadingController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewmodel.users(SessionManager(this).getAccessToken().toString())
        loadingController = LoadingManager(supportFragmentManager)
        setupAction()
        fetchUsers()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun fetchUsers() {
        viewmodel.userState
            .observe(this@UserActivity) { it ->
                when (it) {
                    is ApiResult.Loading -> {
                        loadingController.show("Loading...")
                    }
                    is ApiResult.Success -> {
                        loadingController.hide()
                        setupListUser(it.data.data)
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupListUser(users: List<UserListResponse>?) {

        val adapter = UsersAdapter(users) { camera -> }
        binding.rvUser.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            removeDuration = 200
            moveDuration = 200
        }
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@UserActivity)
            this.adapter = adapter
        }
    }

    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            this@UserActivity.finish()
        }

    }
}