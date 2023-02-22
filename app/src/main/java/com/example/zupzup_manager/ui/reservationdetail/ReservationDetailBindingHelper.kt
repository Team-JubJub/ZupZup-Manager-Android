package com.example.zupzup_manager.ui.reservationdetail

import com.example.zupzup_manager.domain.models.ReservationModel

class ReservationDetailBindingHelper(
    private val onCreateReservationConfirmBottomSheetButtonClick: (reservation: ReservationModel) -> Unit,
) {
    fun createReservationConfirmBottomSheet(reservation: ReservationModel) {
        onCreateReservationConfirmBottomSheetButtonClick(reservation)
    }
}