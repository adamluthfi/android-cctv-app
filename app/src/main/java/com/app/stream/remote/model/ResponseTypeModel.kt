package com.app.stream.remote.model

typealias LoginApiResponse = BaseResponse<LoginResponse>
typealias TokenApiResponse = BaseResponse<TokenResponse>

typealias UserApiResponse = BaseResponse<UserResponse>
typealias UsersApiResponse = BaseResponse<List<UserResponse>>

typealias RoleApiResponse = BaseResponse<RoleResponse>
typealias RolesApiResponse = BaseResponse<List<RoleResponse>>

typealias PermissionApiResponse = BaseResponse<List<Permission>>

typealias ChannelApiResponse = BaseResponse<ChannelCameraResponse>
typealias ChannelsApiResponse = BaseResponse<List<ChannelCameraResponse>>

typealias CameraApiResponse = BaseResponse<List<Camera>>