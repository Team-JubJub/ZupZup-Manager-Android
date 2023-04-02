package com.example.zupzup_manager.ui.reservationdetail

import com.example.zupzup_manager.domain.models.CartModel

interface HandleReservationBtnClickListener {

    fun confirmReservation(reserveId : Long, cartList : List<CartModel>)

    fun rejectReservation(reserveId: Long)

    fun cancelReservation(reserveId : Long)

    fun completeReservation(reserveId: Long)
}