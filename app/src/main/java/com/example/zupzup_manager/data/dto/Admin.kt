package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.AdminModel

data class Admin(
    val id: String ="",
    val pw: String = "",
    val storeId: Long = 0L
) {
    fun toModel(): AdminModel {
        return AdminModel(
            id = id,
            pw = pw,
            storeId = storeId
        )
    }
}



