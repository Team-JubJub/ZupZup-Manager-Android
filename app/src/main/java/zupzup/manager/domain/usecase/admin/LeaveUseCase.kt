package zupzup.manager.domain.usecase.admin

import zupzup.manager.domain.repository.SignInRepository
import javax.inject.Inject

class LeaveUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(id: Long): Result<String> {
        return signInRepository.leave(id)
    }
}