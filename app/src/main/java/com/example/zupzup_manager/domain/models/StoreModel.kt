package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel (
    val storeId: Long,
    val name: String,
    val openTime: String,
    val hostPhoneNumber: String,
    val location: Pair<Double, Double>,
    val address: String,
    val eventList: List<String>,
    val merchandiseList: List<MerchandiseModel>,
    val saleTime: Pair<Int, Int>
): Parcelable