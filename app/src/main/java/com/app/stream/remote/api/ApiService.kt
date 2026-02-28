package com.app.stream.remote.api

import com.app.stream.remote.model.CameraApiResponse
import com.app.stream.remote.model.ChannelApiResponse
import com.app.stream.remote.model.ChannelManageCameraReq
import com.app.stream.remote.model.ChannelsApiResponse
import com.app.stream.remote.model.LoginApiResponse
import com.app.stream.remote.model.LoginRequest
import com.app.stream.remote.model.LoginResponse
import com.app.stream.remote.model.RefreshTokenRequest
import com.app.stream.remote.model.RolesApiResponse
import com.app.stream.remote.model.TokenApiResponse
import com.app.stream.remote.model.TokenResponse
import com.app.stream.remote.model.UpdateUserRequest
import com.app.stream.remote.model.User
import com.app.stream.remote.model.UserApiResponse
import com.app.stream.remote.model.UsersApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // AUTH
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginApiResponse

    @POST("auth/refresh")
    suspend fun refresh(
        @Body request: RefreshTokenRequest
    ): TokenApiResponse

    // USER
    @GET("users")
    suspend fun getUsers(): UsersApiResponse

    @POST("users")
    suspend fun createUser(
        @Body request: User
    ): UserApiResponse

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body request: UpdateUserRequest
    ): UserApiResponse

    // ROLE
    @GET("roles")
    suspend fun getRoles(): RolesApiResponse

    // CHANNEL
    @GET("channels")
    suspend fun getChannels(
        @Header("Authorization") token: String,
    ): ChannelsApiResponse

    @PUT("channels/{channelId}/cameras")
    suspend fun updateChannelCamera(
        @Path("channelId") id: Long,
        @Body request: ChannelManageCameraReq
    ): ChannelApiResponse

    @GET("camera/channel/{channelId}")
    suspend fun getCameras(
        @Path("channelId") id: Long,
        @Header("Authorization") token: String,
    ): CameraApiResponse
}