package com.app.stream.module.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.remote.ApiResult
import com.app.stream.module.main.repository.StreamRepository
import com.app.stream.remote.model.LoginApiResponse
import com.app.stream.remote.model.LoginResponse
import kotlinx.coroutines.launch

class StreamViewModel: ViewModel() {

    private val repository = StreamRepository()
    private val _loginState = MutableLiveData<ApiResult<LoginApiResponse>>()
    val loginState: LiveData<ApiResult<LoginApiResponse>> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            _loginState.value = repository.login(username, password)
        }
    }
}