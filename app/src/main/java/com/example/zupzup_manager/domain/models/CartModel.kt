package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartModel(
    val itemId: Long,
    val storeId: Long,
    val name: String,
    val salesPrice: Int,
    val amount: Int
) : Parcelable
