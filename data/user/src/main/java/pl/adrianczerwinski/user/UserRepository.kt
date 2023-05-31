package pl.adrianczerwinski.user

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.user.model.User

interface UserRepository {
    suspend fun setOnboardingShown()
    fun getOnboardingShown(): Flow<Boolean?>
    fun getUser(): Flow<User?>
    suspend fun saveUser(user: User)
}
