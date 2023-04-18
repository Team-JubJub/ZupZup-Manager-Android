package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(id: String, pw: String): Flow<DataResult<Int>> {
        return flow {
            adminRepository.login(id, pw).onSuccess {
                emit(DataResult.Success(it))
            }.onFailure {
                if (it is NullPointerException) {
                    emit(DataResult.Failure(0))
                }
                emit(DataResult.Failure(1))
            }
        }.flowOn(Dispatchers.IO)
    }

}