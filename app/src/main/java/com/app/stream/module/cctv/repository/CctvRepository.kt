package com.app.stream.module.cctv.repository

import com.app.stream.remote.ApiResult
import com.app.stream.remote.NetworkModule
import com.app.stream.remote.api.ApiService
import com.app.stream.remote.model.CameraApiResponse
import com.app.stream.remote.model.ChannelsApiResponse

class CctvRepository(
    private val api: ApiService = NetworkModule.create(ApiService::class.java)
) {
    suspend fun cameras(token: String, channelId: Long): ApiResult<CameraApiResponse> {
        return try {
            val response = api.getCameras(channelId, "Bearer $token")
            if (response.status == 200) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(
                    response.message ?: "get channels failed",
                    response.status
                )
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network error")
        }
    }
}