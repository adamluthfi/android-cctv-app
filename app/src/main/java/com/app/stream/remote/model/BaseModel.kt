package com.app.stream.remote.model

data class BaseResponse<T>(
    val timestamp: String?,
    val traceId: String?,
    val status: Int?,
    val message: String?,
    val httpStatus: String?,
    val data: T?
)