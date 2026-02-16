package com.app.stream.module.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.LoginResponse
import com.app.stream.module.main.repository.StreamRepository
import kotlinx.coroutines.launch

class StreamViewModel: ViewModel() {

    private val repository = StreamRepository()

    private val _loginState = MutableLiveData<ApiResult<LoginResponse>>()
    val loginState: LiveData<ApiResult<LoginResponse>> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            _loginState.value = repository.login(username, password)
        }
    }
}