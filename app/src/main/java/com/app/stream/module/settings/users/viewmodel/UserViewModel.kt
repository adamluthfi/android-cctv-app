package com.app.stream.module.settings.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.module.settings.users.repository.UserRepository
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.UserListApiResponse
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val repository = UserRepository()

    private val _userState = MutableLiveData<ApiResult<UserListApiResponse>>()
    val userState: LiveData<ApiResult<UserListApiResponse>> = _userState

    fun users(token: String) {
        viewModelScope.launch {
            _userState.value = ApiResult.Loading
            _userState.value = repository.users(token)
        }
    }
}