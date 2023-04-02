package com.example.zupzup_manager.data.datasource.lunasoft

import com.example.zupzup_manager.data.common.Constants
import com.example.zupzup_manager.data.dto.lunasoft.parameter.LunaSoftRequestBody
import com.example.zupzup_manager.data.dto.lunasoft.parameter.Message
import com.example.zupzup_manager.data.dto.lunasoft.response.LunaSoftResponse
import com.example.zupzup_manager.data.service.LunaSoftService
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
            userId = Constants.userId,
            apiKey = Constants.apiKey,
            templateId = templateId,
            messages = messages
        )
        return lunaSoftService.sendNotificationTalk(parameter = body)
    }
}