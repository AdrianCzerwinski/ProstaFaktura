package pl.adrianczerwinski.domain.user

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.common.toCatchingResult
import pl.adrianczerwinski.user.UserRepository
import javax.inject.Inject

class GetUserRegisteredUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<Boolean?>> = userRepository.getUserRegistered().toCatchingResult()
}
