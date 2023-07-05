package pl.adrianczerwinski.domain.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.adrianczerwinski.client.ClientRepository
import pl.adrianczerwinski.client.model.Client
import pl.adrianczerwinski.common.resultOf
import javax.inject.Inject

class SaveClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
) {
    suspend operator fun invoke(client: Client) = withContext(Dispatchers.IO) {
        resultOf {
            clientRepository.saveClient(client)
        }
    }
}
