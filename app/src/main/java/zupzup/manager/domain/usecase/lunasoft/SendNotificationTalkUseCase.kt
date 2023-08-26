package zupzup.manager.domain.usecase.lunasoft

import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.repository.LunaSoftRepository
import javax.inject.Inject

class SendNotificationTalkUseCase @Inject constructor(
    private val lunaSoftRepository: LunaSoftRepository
) {
    suspend operator fun invoke(orderModel: OrderModel, state: String): Result<Int> {
        return lunaSoftRepository.sendNotificationTalk(orderModel, state)
    }
}