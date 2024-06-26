package zupzup.manager.domain.usecase.admin

import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.admin.AdminModel
import zupzup.manager.domain.repository.SignInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(id: String, pw: String, deviceToken: String): Flow<DataResult<AdminModel>> {
        return flow {
            signInRepository.login(id, pw, deviceToken).onSuccess {
                when (it.accessToken) {
                    "-1" -> {
                        emit(DataResult.Failure("패스워드를 잘못 입력했습니다."))
                    }
                    else -> {
                        emit(DataResult.Success(it))
                    }
                }
            }.onFailure {
                when (it) {
                    is IOException -> {
                        emit(DataResult.Failure("아이디를 잘못 입력했습니다."))
                    }
                    else -> {
                        emit(DataResult.Failure("알 수 없는 에러 발생"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}