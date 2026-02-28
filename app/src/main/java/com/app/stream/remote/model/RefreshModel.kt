package com.app.stream.remote.model

data class RefreshTokenRequest(
    val refreshToken: String?
)

data class TokenResponse(
    val accessToken: String?,
    val refreshToken: String?
)