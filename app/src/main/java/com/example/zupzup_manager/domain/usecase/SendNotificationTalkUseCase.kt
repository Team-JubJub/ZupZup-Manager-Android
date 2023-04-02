package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.repository.LunaSoftRepository
import javax.inject.Inject

class SendNotificationTalkUseCase @Inject constructor(
    private val lunaSoftRepository: LunaSoftRepository
) {
    suspend operator fun invoke(reservationModel: ReservationModel, state: String): Result<Int> {
        return lunaSoftRepository.sendNotificationTalk(reservationModel, state)
    }
}