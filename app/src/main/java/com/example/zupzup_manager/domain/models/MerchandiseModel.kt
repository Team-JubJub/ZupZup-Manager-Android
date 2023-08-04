package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchandiseModel(
    val itemId: Long,
    val itemName: String,
    val imageUrl: String,
    val itemPrice: Int,
    val salePrice: Int,
    var itemCount: Int
): Parcelable {
    private var _modifiedStock = itemCount
    val modifiedStock get() = _modifiedStock

    fun plusModifiedAmount() {
        _modifiedStock = _modifiedStock.plus(1)
    }

    fun minusModifiedAmount() {
        _modifiedStock = _modifiedStock.minus(1)
    }
}