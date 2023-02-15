package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.ReservationModel

data class ReservationDto(
    val storeId: Long,
    val reserveId: Long,
    val state: String,
    val visitTime: Int,
    val customer: CustomerDto,
    val cartList: List<CartDto>
) {
    fun toReservationModel(): ReservationModel {
        return ReservationModel(
            storeId = storeId,
            reserveId = reserveId,
            state = state,
            visitTime = visitTime,
            customer = customer.toCustomerModel(),
            cartList = cartList.map { it.toCartModel() }
        )
    }
}