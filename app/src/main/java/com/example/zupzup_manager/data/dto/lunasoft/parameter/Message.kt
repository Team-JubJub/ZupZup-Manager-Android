package com.example.zupzup_manager.data.dto.lunasoft.parameter
import com.google.gson.annotations.SerializedName

data class Message(
    val no : String,
    @SerializedName("tel_num") val telNum : String,
    @SerializedName("msg_content") val msgContent : String,
    @SerializedName("sms_content") val smsContent : String,
    @SerializedName("use_sms") val useSms : String = "0",
)
