package zupzup.manager.data.dto.lunasoft.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    val no: String,
    @SerializedName("tel_num") val temNum: String,
    @SerializedName("result_code") val resultCode: String,
    @SerializedName("result_msg") val resultMsg: String
)
