package zupzup.manager.domain.repository

import zupzup.manager.domain.models.order.OrderModel

interface LunaSoftRepository {

    suspend fun sendNotificationTalk(orderModel : OrderModel, state : String) : Result<Int>
}