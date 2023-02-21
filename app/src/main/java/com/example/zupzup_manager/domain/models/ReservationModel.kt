package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationModel(
    val storeId: Long,
    val reserveId: Long,
    val state: String,
    val visitTime: Int,
    val customer: CustomerModel,
    val cartList: List<CartModel>
) : Parcelable
