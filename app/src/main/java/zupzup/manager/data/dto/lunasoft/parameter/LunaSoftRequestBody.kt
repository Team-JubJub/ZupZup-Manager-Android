package zupzup.manager.data.dto.lunasoft.parameter

import com.google.gson.annotations.SerializedName

data class LunaSoftRequestBody(
    @SerializedName("userid") val userId: String,
    @SerializedName("api_key") val apiKey: String,
    @SerializedName("template_id") val templateId: Int,
    val messages: List<Message>
)