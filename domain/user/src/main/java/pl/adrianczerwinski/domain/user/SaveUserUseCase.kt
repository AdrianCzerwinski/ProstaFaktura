package pl.adrianczerwinski.domain.user

import pl.adrianczerwinski.common.resultOf
import pl.adrianczerwinski.user.UserRepository
import pl.adrianczerwinski.user.model.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) = resultOf {
        userRepository.saveUser(user)
    }
}
