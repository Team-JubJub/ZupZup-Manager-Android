package com.example.zupzup_manager.data.datasource.lunasoft

import com.example.zupzup_manager.data.dto.lunasoft.parameter.Message
import com.example.zupzup_manager.data.dto.lunasoft.response.LunaSoftResponse
import retrofit2.Response

interface LunaSoftDataSource {

    suspend fun sendNotificationTalkToCustomer(
        messages: List<Message>,
        templateId: Int
    ): Response<LunaSoftResponse>
}