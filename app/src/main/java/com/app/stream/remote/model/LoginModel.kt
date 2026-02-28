package com.app.stream.remote.model

data class LoginRequest(
    val username: String?,
    val password: String?
)
data class LoginResponse(
    val id: Long?,
    val username: String?,
    val accessToken: String?,
    val refreshToken: String?
)