package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderModel(
    val orderId: Long,
    val storeId: Long,
    val customer: CustomerModel,
    val orderStatus: String,
    val orderTitle: String,
    val orderTime: String,
    val visitTime: String,
    val storeName: String,
    val storeAddress: String,
    val category: String,
    val orderList: List<OrderSpecificModel>
) : Parcelable

