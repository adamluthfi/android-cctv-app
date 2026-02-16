package com.app.stream.module.main.repository

import com.app.stream.remote.ApiResult
import com.app.stream.remote.NetworkModule
import com.app.stream.remote.api.AuthApi
import com.app.stream.remote.model.LoginRequest
import com.app.stream.remote.model.LoginResponse

class StreamRepository(
    private val api: AuthApi = NetworkModule.create(AuthApi::class.java)
) {
    suspend fun login(
        username: String,
        password: String
    ): ApiResult<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(
                    response.errorBody()?.string() ?: "Login failed",
                    response.code()
                )
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network error")
        }
    }
}
