package com.app.stream.module.main.repository

import com.app.stream.remote.ApiResult
import com.app.stream.remote.NetworkModule
import com.app.stream.remote.api.ApiService
import com.app.stream.remote.model.LoginApiResponse
import com.app.stream.remote.model.LoginRequest
import com.app.stream.remote.model.LoginResponse

class StreamRepository(
    private val api: ApiService =  NetworkModule.create(ApiService::class.java)
) {
    suspend fun login(
        username: String,
        password: String
    ): ApiResult<LoginApiResponse> {
        return try {
            val response = api.login(LoginRequest(username, password))
            if (response.status == 200) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(
                    response.message ?: "Login failed",
                    response.status
                )
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network error")
        }
    }
}
