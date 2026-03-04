package com.app.stream.module.settings.users.repository

import com.app.stream.remote.ApiResult
import com.app.stream.remote.NetworkModule
import com.app.stream.remote.api.ApiService
import com.app.stream.remote.model.UserListApiResponse

class UserRepository(
    private val api: ApiService =  NetworkModule.create(ApiService::class.java)
) {
    suspend fun users(
        token: String
    ): ApiResult<UserListApiResponse> {
        return try {
            val response = api.getUsers("Bearer $token")
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
}