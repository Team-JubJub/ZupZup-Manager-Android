package com.example.zupzup_manager.domain.models.admin

data class AdminModel(
    val result : String,
    val message : String,
    val accessToken : String,
    val refreshToken : String,
    val storeId : Long
)
