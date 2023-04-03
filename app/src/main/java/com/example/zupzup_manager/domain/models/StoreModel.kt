package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel (
    val storeId: Long,
    val name: String,
    val openTime: String,
    val hostPhoneNumber: String,
    val longitude: Double,
    val latitude: Double,
    val address: String,
    val eventList: List<String>,
    val merchandiseList: List<MerchandiseModel>,
    val saleTimeStart: Int = 0,
    val saleTimeEnd: Int = 0
): Parcelable