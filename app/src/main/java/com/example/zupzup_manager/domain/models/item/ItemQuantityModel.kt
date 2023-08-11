package com.example.zupzup_manager.domain.models.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemQuantityModel (
    val itemId: Long,
    var itemCount: Int
): Parcelable