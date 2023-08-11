package com.example.zupzup_manager.data.dto.admin.response

import com.example.zupzup_manager.domain.models.admin.AdminModel

data class SignInResponse(
    val result: String,
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val storeId: Long
) {
    fun toAdminModel(): AdminModel {
        return AdminModel(
            result = result,
            message = message,
            accessToken = accessToken,
            refreshToken = refreshToken,
            storeId = storeId
        )
    }
}
