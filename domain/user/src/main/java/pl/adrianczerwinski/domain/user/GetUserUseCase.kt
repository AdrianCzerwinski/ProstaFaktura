package pl.adrianczerwinski.domain.user

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.common.toCatchingResult
import pl.adrianczerwinski.user.UserRepository
import pl.adrianczerwinski.user.model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<User?>> = userRepository.getUser().toCatchingResult()
}
