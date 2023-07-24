package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderSpecificModel(
    val itemId: Long,
    val itemName: String,
    val itemPrice: Int,
    val itemCount: Int
) : Parcelable
