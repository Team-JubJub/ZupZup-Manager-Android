package com.example.zupzup_manager.ui.reservationdetail

import com.example.zupzup_manager.domain.models.ReservationModel

interface ReservationDetailFragmentClickListener {

    fun confirmReservation(reservationModel: ReservationModel, isPartial: Boolean)

    fun rejectReservation(reservationModel: ReservationModel)

    fun cancelReservation(reserveId: Long)

    fun completeReservation(reserveId: Long)

    fun onBackBtnClick()
}