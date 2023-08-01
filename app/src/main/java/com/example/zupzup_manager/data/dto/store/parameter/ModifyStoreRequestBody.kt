package com.example.zupzup_manager.data.dto.store.parameter

import okhttp3.MultipartBody

data class ModifyStoreRequestBody(
    val openTime: String,
    val closeTime: String,
    val saleTimeStart: String,
    val saleTimeEnd: String,
    val closedDay: String
)