package zupzup.manager.data.repository

import zupzup.manager.data.datasource.lunasoft.LunaSoftDataSource
import zupzup.manager.data.dto.mapper.LunaSoftMessageMapper
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.repository.LunaSoftRepository
import javax.inject.Inject

class LunaSoftRepositoryImpl @Inject constructor(
    private val lunaSoftDataSource: LunaSoftDataSource
) : LunaSoftRepository {
    override suspend fun sendNotificationTalk(
        orderModel: OrderModel,
        state: String
    ): Result<Int> {
        return try {
            val messages = LunaSoftMessageMapper.getNotificationTalkMessage(orderModel, state)
            val templateId = LunaSoftMessageMapper.getNotificationTalkTemplateId(state)
            val result = lunaSoftDataSource.sendNotificationTalkToCustomer(messages, templateId)
            if (result.isSuccessful) {
                when (result.body()?.code) {
                    0 -> Result.success(0)
                    else -> {
                        Result.success(-1)
                    }
                }
            } else {
                Result.success(result.code())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}