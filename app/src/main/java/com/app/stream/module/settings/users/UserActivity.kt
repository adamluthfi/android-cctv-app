package com.app.stream.module.settings.users

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.common.SwipeToDeleteCallback
import com.app.stream.common.adapter.UsersAdapter
import com.app.stream.databinding.ActivityUserBinding
import com.app.stream.module.settings.users.viewmodel.UserViewModel
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.UserListResponse
import com.app.stream.ui.common.loading.LoadingController
import com.app.stream.ui.common.loading.LoadingManager
import com.google.android.material.snackbar.Snackbar
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
                        setupListUser(it.data.data?.toMutableList())
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun deleteUser() {
        viewmodel.deleteState
            .observe(this@UserActivity) { it ->
                when (it) {
                    is ApiResult.Loading -> {
                        loadingController.show("Loading...")
                    }
                    is ApiResult.Success -> {
                        loadingController.hide()
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupListUser(users: MutableList<UserListResponse>?) {

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

        val swipeHandler = SwipeToDeleteCallback(applicationContext) { position ->

            val deletedItem = adapter.items?.get(position)
            adapter.deleteItem(position)
            viewmodel.deleteUser(deletedItem?.id, SessionManager(this).getAccessToken().toString())
            deleteUser()
            Snackbar.make(binding.root, "User deleted", Snackbar.LENGTH_LONG).show()
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvUser)
    }

    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            this@UserActivity.finish()
        }

    }
}