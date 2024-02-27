package zupzup.manager.domain.usecase.admin

import zupzup.manager.domain.repository.SignInRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        deviceToken: String
    ): Result<String> {
        return signInRepository.logout(accessToken, refreshToken, deviceToken)
    }
}