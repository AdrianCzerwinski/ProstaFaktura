package pl.adrianczerwinski.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun setUserRegistered()
    fun getUserRegistered(): Flow<Boolean?>
}
