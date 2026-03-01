package com.app.stream.remote.model

data class UserResponse(
    val username: String?,
    val enabled: Boolean?,
    val createdBy: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val role: RoleUserResponse?,
    val channels: List<UserChannelResponse>?
)

data class UpdateUserRequest(
    val username: String?,
    val roleId: Long?,
    val channelIds: List<Long>?
)

data class User(
    val username: String?,
    val password: String?,
    val enabled: Boolean?,
    val channels: List<Channel>?
)

data class UserChannelResponse(
    val id: Long?,
    val name: String?,
    val cameras: List<ChannelResponse>?
)