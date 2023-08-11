package com.example.zupzup_manager.domain.usecase.lunasoft

import com.example.zupzup_manager.domain.models.order.OrderModel
import com.example.zupzup_manager.domain.repository.LunaSoftRepository
import javax.inject.Inject

class SendNotificationTalkUseCase @Inject constructor(
    private val lunaSoftRepository: LunaSoftRepository
) {
    suspend operator fun invoke(orderModel: OrderModel, state: String): Result<Int> {
        return lunaSoftRepository.sendNotificationTalk(orderModel, state)
    }
}