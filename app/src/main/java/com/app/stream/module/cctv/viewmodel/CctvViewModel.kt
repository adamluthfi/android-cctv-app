package com.app.stream.module.cctv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stream.module.cctv.repository.CctvRepository
import com.app.stream.remote.ApiResult
import com.app.stream.remote.model.CameraApiResponse
import com.app.stream.remote.model.ChannelsApiResponse
import kotlinx.coroutines.launch

class CctvViewModel: ViewModel() {
    private val repository = CctvRepository()

    private val _cctvState = MutableLiveData<ApiResult<CameraApiResponse>>()
    val cctvState: LiveData<ApiResult<CameraApiResponse>> = _cctvState

    fun cameras(token: String, channelID: Long) {
        viewModelScope.launch {
            _cctvState.value = ApiResult.Loading
            _cctvState.value = repository.cameras(token, channelID)
        }
    }
}