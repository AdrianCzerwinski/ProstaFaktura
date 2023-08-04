package pl.adrianczerwinski.client

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.adrianczerwinski.client.database.ClientDao
import pl.adrianczerwinski.client.database.toClient
import pl.adrianczerwinski.client.database.toModel
import pl.adrianczerwinski.client.model.Client
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val clientDao: ClientDao
) : ClientRepository {
    override fun getClient(taxNumber: String): Flow<Client?> = clientDao.getClient(taxNumber).map { it.toClient() }

    override fun getAllClients(): Flow<List<Client>?> = clientDao.getAllClients().map { it?.map { model -> model.toClient() } }

    override suspend fun saveClient(client: Client) = clientDao.saveClient(client.toModel())
}
