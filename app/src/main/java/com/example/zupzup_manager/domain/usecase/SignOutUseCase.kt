package com.example.zupzup_manager.domain.usecase

import android.util.Log
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.AdminModel
import com.example.zupzup_manager.domain.repository.SignInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): Result<String> {
        return signInRepository.logout(accessToken, refreshToken).onSuccess {
        }.onFailure {
        }
    }
}