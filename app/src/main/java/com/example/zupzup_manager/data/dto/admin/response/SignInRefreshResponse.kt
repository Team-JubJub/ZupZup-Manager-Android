package com.example.zupzup_manager.data.dto.admin.response

data class SignInRefreshResponse(
    val result: String,
    val message: String,
    val providerUserId: String,
    val accessToken: String,
) {
}
