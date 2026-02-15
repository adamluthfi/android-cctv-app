package com.app.stream.model

data class CameraModel(
    val id: String,
    val name: String,
    val location: String,
    val isOnline: Boolean,
    val isRecording: Boolean,
    val previewRes: Int // drawable resource
)
