package com.example.zupzup_manager.data.dto.sign.response

import com.example.zupzup_manager.domain.models.AdminModel

data class SignInResponse(
    val message: String,
    val fireBaseStoreId: Long
) {
    fun toAdminModel(): AdminModel {
        return AdminModel(
            message = message,
            storeId = fireBaseStoreId
        )
    }
}
