package zupzup.manager.data.datasource.lunasoft

import zupzup.manager.data.dto.lunasoft.parameter.LunaSoftRequestBody
import zupzup.manager.data.dto.lunasoft.parameter.Message
import zupzup.manager.data.dto.lunasoft.response.LunaSoftResponse
import zupzup.manager.data.service.LunaSoftService
import retrofit2.Response
import javax.inject.Inject

class LunaSoftDateSourceImpl @Inject constructor(
    private val lunaSoftService: LunaSoftService
) : LunaSoftDataSource {
    override suspend fun sendNotificationTalkToCustomer(
        messages: List<Message>,
        templateId: Int
    ): Response<LunaSoftResponse> {
        val body = LunaSoftRequestBody(
            userId = zupzup.manager.data.common.Constants.userId,
            apiKey = zupzup.manager.data.common.Constants.apiKey,
            templateId = templateId,
            messages = messages
        )
        return lunaSoftService.sendNotificationTalk(parameter = body)
    }
}