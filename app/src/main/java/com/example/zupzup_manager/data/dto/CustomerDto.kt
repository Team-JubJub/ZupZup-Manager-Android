package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.CustomerModel

data class CustomerDto(
    val name: String,
    val phoneNumber: String
) {
    fun toCustomerModel() : CustomerModel {
        return CustomerModel(
            name = name,
            phoneNumber = phoneNumber
        )
    }
}