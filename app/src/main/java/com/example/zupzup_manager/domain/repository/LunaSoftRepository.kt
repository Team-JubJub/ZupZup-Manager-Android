package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.OrderModel

interface LunaSoftRepository {

    suspend fun sendNotificationTalk(orderModel : OrderModel, state : String) : Result<Int>
}