package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel (
    val storeId: Long = 0,
    val sellerId: Long = 0,
    val storeName: String = "",
    val storeImageUrl: String = "",
    val storeAddress: String = "",
    val category: String = "",
    val contact: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val openTime: String = "",
    val closeTime: String = "",
    val saleTimeStart: String = "",
    val saleTimeEnd: String = "",
    val saleMatters: String = "",
    val isOpen: Boolean = false,
    val closedDay: String? = ""
): Parcelable