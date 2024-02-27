package zupzup.manager.data.datasource.lunasoft

import zupzup.manager.data.dto.lunasoft.parameter.Message
import zupzup.manager.data.dto.lunasoft.response.LunaSoftResponse
import retrofit2.Response

interface LunaSoftDataSource {

    suspend fun sendNotificationTalkToCustomer(
        messages: List<Message>,
        templateId: Int
    ): Response<LunaSoftResponse>
}