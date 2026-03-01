package com.app.stream.module.settings.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.stream.R
import com.app.stream.common.SessionManager
import com.app.stream.databinding.ActivityRegisterBinding
import com.app.stream.module.home.viewmodel.HomeViewModel
import com.app.stream.module.settings.registration.viewmodel.RegistrationViewModel
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.Channel
import com.app.stream.remote.model.ChannelCameraResponse
import com.app.stream.remote.model.User
import com.app.stream.ui.common.loading.LoadingController
import com.app.stream.ui.common.loading.LoadingManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.internal.wait
import kotlin.getValue

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewmodel = RegistrationViewModel()
    private val channel = mutableListOf<Channel>()
    private lateinit var loadingController: LoadingController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        loadingController = LoadingManager(supportFragmentManager)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewmodel.channels(SessionManager(this).getAccessToken().toString())
        setupButton()
        setupEditText()
        setListDropDown()

        viewmodel.registrationState
            .observe(this@RegisterActivity) { it ->
                when (it) {
                    is ApiResult.Loading -> {
                        loadingController.show("Loading...")
                    }
                    is ApiResult.Success -> {
                        loadingController.hide()
                        this@RegisterActivity.finish()
                        Toast.makeText(this, it.data.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

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
            this@RegisterActivity.finish()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.tietUserId.text?.isEmpty() == true) {
                binding.tiluserID.error = "User ID is required"
                binding.tiluserID.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tiePassword.text?.isEmpty() == true) {
                binding.tilPassword.error = "Password is required"
                binding.tilPassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietrepassword.text?.isEmpty() == true) {
                binding.tilrepassword.error = "Confirm password is required"
                binding.tilrepassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.tietrepassword.text.toString() != binding.tiePassword.text.toString()) {
                binding.tilrepassword.error = "Password does not match"
                binding.tilrepassword.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            } else if (binding.dropdownRole.text.isEmpty()) {
                binding.tildropdown.error = "channel is required"
                binding.tildropdown.boxStrokeColor = getColor(R.color.maroon)
                return@setOnClickListener
            }  else {
                binding.tiluserID.error = null
                binding.tilPassword.error = null
                binding.tilrepassword.error = null
                binding.tildropdown.error = null
                val username = binding.tietUserId.text.toString()
                val password = binding.tietrepassword.text.toString()
                val user = User(username, password, true, channel)
                viewmodel.users(SessionManager(this).getAccessToken().toString(), user)
            }
        }
    }

    private fun setListDropDown() {
        val items = mutableListOf<ChannelCameraResponse?>()
        viewmodel.channelState
            .observe(this@RegisterActivity) { it ->
                when (it) {
                    is ApiResult.Loading -> {
                        loadingController.show("Loading...")
                    }
                    is ApiResult.Success -> {
                        loadingController.hide()
                        it.data.data?.forEach {
                            items.add(it)
                        }
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items.map { it?.name })
                        binding.dropdownRole.setAdapter(adapter)
                        binding.dropdownRole.setOnItemClickListener { parent, view, position, id ->
                            val selectedId = items[position]?.id
                            val channelId = Channel(selectedId)
                            channel.add(channelId)
                        }
                    }
                    is ApiResult.Error -> {
                        loadingController.hide()
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupEditText() {
        setupTextInput(binding.tiluserID, binding.tietUserId)
        setupTextInput(binding.tilPassword, binding.tiePassword)
        setupTextInput(binding.tilrepassword, binding.tietrepassword)
        setupTextInput(binding.tildropdown, binding.tietUserId)
    }

    private fun setupTextInput(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText) {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                textInputLayout.boxStrokeColor = getColor(R.color.purple_500)
                textInputLayout.error = null
            }
        })
    }
}