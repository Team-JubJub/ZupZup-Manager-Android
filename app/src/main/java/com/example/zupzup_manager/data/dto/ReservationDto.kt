package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.CustomerModel
import com.example.zupzup_manager.domain.models.ReservationModel

data class ReservationDto(
    val storeId: Long = 0,
    val reserveId: Long = 0,
    val state: String = "",
    val visitTime: Int = 0,
    val customerName: String = "",
    val customerPhone : String ="",
    val cartList: List<CartDto> = listOf()
) {
    fun toReservationModel(): ReservationModel {
        return ReservationModel(
            storeId = storeId,
            reserveId = reserveId,
            state = state,
            visitTime = visitTime,
            customer = CustomerModel(customerName, customerPhone),
            cartList = cartList.map { it.toCartModel() }
        )
    }
}