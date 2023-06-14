package pl.adrianczerwinski.client

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.client.model.Client

interface ClientRepository {
    fun getClient(taxNumber: String): Flow<Client?>
    fun getAllClients(): Flow<List<Client>?>
    suspend fun saveClient(client: Client)
}
