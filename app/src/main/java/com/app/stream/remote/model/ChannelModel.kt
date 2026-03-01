package com.app.stream.remote.model

data class Channel(
    val id: Long?
)

data class ChannelResponse(
    val id: Long?,
    val name: String?
)

data class ChannelCameraResponse(
    val id: Long?,
    val name: String?,
    val cameras: List<ChannelResponse>?,
    val createdBy: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class ChannelManageCameraReq(
    val cameraIds: List<Long>?
)
