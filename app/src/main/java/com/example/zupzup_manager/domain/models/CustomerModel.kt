package com.example.zupzup_manager.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerModel(
    val id: Long,
    val name: String,
    val phoneNumber: String,
) : Parcelable