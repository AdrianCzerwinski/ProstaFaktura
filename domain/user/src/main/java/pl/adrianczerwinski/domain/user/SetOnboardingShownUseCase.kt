package pl.adrianczerwinski.domain.user

import pl.adrianczerwinski.user.UserRepository
import javax.inject.Inject

class SetOnboardingShownUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.setOnboardingShown()
}
