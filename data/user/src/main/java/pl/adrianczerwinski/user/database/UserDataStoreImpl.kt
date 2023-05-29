package pl.adrianczerwinski.user.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES = "user_preferences"

class UserDataStoreImpl(@ApplicationContext private val context: Context) : UserDataStore {

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)
    override fun getBoolean(key: String): Flow<Boolean?> = context.userPreferencesDataStore.data.map { it[booleanPreferencesKey(key)] }

    override suspend fun putBoolean(key: String, value: Boolean) {
        context.userPreferencesDataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    override fun getString(key: String): Flow<String?> = context.userPreferencesDataStore.data.map { it[stringPreferencesKey(key)] }

    override suspend fun putString(key: String, value: String) {
        context.userPreferencesDataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }
}
