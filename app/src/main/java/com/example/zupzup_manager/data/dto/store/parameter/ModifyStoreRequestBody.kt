package com.example.zupzup_manager.data.dto.store.parameter

data class ModifyStoreRequestBody(
    val openTime: String,
    val closeTime: String,
    val saleTimeStart: String,
    val saleTimeEnd: String,
    val closedDay: String
)