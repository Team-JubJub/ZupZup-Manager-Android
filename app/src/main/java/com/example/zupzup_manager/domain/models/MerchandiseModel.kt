package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchandiseModel(
    val itemId: Long,
    val storeId: Long,
    val itemName: String,
    val price: Int,
    val discounted: Int,
    val imgUrl: String,
    val stock: Int,
): Parcelable