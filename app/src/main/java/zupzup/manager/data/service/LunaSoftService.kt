package zupzup.manager.data.service

import zupzup.manager.data.dto.lunasoft.parameter.LunaSoftRequestBody
import zupzup.manager.data.dto.lunasoft.response.LunaSoftResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LunaSoftService {

    @POST("api/AlimTalk/message/send")
    suspend fun sendNotificationTalk(
        @Body parameter: LunaSoftRequestBody
    ): Response<LunaSoftResponse>
}