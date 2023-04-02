package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.ReservationModel

interface LunaSoftRepository {

    suspend fun sendNotificationTalk(reservationModel : ReservationModel, state : String) : Result<Int>
}