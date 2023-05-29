package pl.adrianczerwinski.user.database

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    fun getBoolean(key: String): Flow<Boolean?>
    suspend fun putBoolean(key: String, value: Boolean)

    fun getString(key: String): Flow<String?>
    suspend fun putString(key: String, value: String)
}
