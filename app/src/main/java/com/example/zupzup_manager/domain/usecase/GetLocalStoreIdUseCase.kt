package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalStoreIdUseCase @Inject constructor(
    private val adminRepository: SignInRepository
) {
    suspend operator fun invoke(): Flow<DataResult<Triple<String, String, Long>>> {
        return flow {
            adminRepository.getStoreIdInLocal().onSuccess {
                emit(DataResult.Success(it))
            }.onFailure {
                when(it) {
                    is NullPointerException -> {
                        emit(DataResult.Failure("로그인이 필요합니다."))
                    }
                    else -> {
                        emit(DataResult.Failure("알 수 없는 오류 발생"))
                    }
                }
            }
        }
    }
}