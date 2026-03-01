package com.app.stream.module.settings.registration.repository

import com.app.stream.remote.ApiResult
import com.app.stream.remote.NetworkModule
import com.app.stream.remote.api.ApiService
import com.app.stream.remote.model.ChannelsApiResponse
import com.app.stream.remote.model.User
import com.app.stream.remote.model.UserApiResponse

class RegistrationRepository (
    private val api: ApiService =  NetworkModule.create(ApiService::class.java)
) {
    suspend fun users(
        token: String,
        user: User
    ): ApiResult<UserApiResponse> {
        return try {
            val response = api.createUser("Bearer $token", user)
            if (response.status == 200) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(
                    response.message ?: "create user failed",
                    response.status
                )
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network error")
        }
    }

    suspend fun channels(token: String): ApiResult<ChannelsApiResponse> {
        return try {
            val response = api.getChannels(token = "Bearer $token")
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