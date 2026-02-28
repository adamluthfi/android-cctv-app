package com.app.stream.module.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.module.home.repository.HomeRepository
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.ChannelsApiResponse
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = HomeRepository()

    private val _homeState = MutableLiveData<ApiResult<ChannelsApiResponse>>()
    val homeState: LiveData<ApiResult<ChannelsApiResponse>> = _homeState

    fun channels(token: String) {
        viewModelScope.launch {
            _homeState.value = ApiResult.Loading
            _homeState.value = repository.channels(token)
        }
    }
}