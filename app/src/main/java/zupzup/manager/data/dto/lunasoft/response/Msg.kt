package zupzup.manager.data.dto.lunasoft.response

import com.google.gson.annotations.SerializedName

data class Msg(
    val userid: String,
    @SerializedName("requested_at") val requestedAt: String,
    val messages: List<MessageResponse>
)
