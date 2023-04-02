package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.lunasoft.parameter.LunaSoftRequestBody
import com.example.zupzup_manager.data.dto.lunasoft.response.LunaSoftResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LunaSoftService {

    @POST("api/AlimTalk/message/send")
    suspend fun sendNotificationTalk(
        @Body parameter: LunaSoftRequestBody
    ): Response<LunaSoftResponse>
}