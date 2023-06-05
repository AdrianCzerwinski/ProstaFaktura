package pl.adrianczerwinski.domain.client

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.client.ClientRepository
import pl.adrianczerwinski.client.model.Client
import pl.adrianczerwinski.common.toCatchingResult
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {
    operator fun invoke(taxNumber: String): Flow<Result<Client?>> = clientRepository.getClient(taxNumber).toCatchingResult()
}
