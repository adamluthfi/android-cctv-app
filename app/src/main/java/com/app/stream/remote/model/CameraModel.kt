package com.app.stream.remote.model

data class CameraResponse(
    val id: String,
    val name: String,
    val location: String,
    val isOnline: Boolean,
    val isRecording: Boolean,
    val previewRes: Int // drawable resource
)

data class Camera(
    val id: Long?,
    val name: String?,
    val url: String?
)