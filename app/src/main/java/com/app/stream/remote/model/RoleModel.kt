package com.app.stream.remote.model

data class RoleResponse(
    val id: Long?,
    val name: String?,
    val permissions: List<PermissionResponse>?,
    val createdBy: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class Role(
    val id: Long?,
    val name: String?,
    val permissions: List<Permission>?
)

data class RoleUserResponse(
    val id: Long?,
    val name: String?,
    val permissions: List<PermissionResponse>?
)

data class RolePermissionUpdateRequest(
    val permissionNames: List<String>?
)