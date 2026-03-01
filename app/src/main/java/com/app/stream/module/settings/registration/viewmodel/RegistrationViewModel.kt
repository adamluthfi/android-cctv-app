package com.app.stream.module.settings.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.module.home.repository.HomeRepository
import com.app.stream.module.settings.registration.repository.RegistrationRepository
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.ChannelsApiResponse
import com.app.stream.remote.model.LoginApiResponse
import com.app.stream.remote.model.User
import com.app.stream.remote.model.UserApiResponse
import kotlinx.coroutines.launch

class RegistrationViewModel: ViewModel() {

    private val repository = RegistrationRepository()

    private val _registrationState = MutableLiveData<ApiResult<UserApiResponse>>()
    val registrationState: LiveData<ApiResult<UserApiResponse>> = _registrationState

    private val _channelState = MutableLiveData<ApiResult<ChannelsApiResponse>>()
    val channelState: LiveData<ApiResult<ChannelsApiResponse>> = _channelState

    fun users(token: String, user: User) {
        viewModelScope.launch {
            _registrationState.value = ApiResult.Loading
            _registrationState.value = repository.users(token, user)
        }
    }

    fun channels(token: String) {
        viewModelScope.launch {
            _channelState.value = ApiResult.Loading
            _channelState.value = repository.channels(token)
        }
    }
}