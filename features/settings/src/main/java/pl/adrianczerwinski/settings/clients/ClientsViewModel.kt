package pl.adrianczerwinski.settings.clients

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.domain.client.GetAllClientsUseCase
import pl.adrianczerwinski.settings.clients.ClientsUiAction.NavigateUp
import pl.adrianczerwinski.settings.clients.ClientsUiAction.OpenAddClient
import pl.adrianczerwinski.settings.clients.ClientsUiAction.OpenClient
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.AddClientPressed
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.BackPressed
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.EditClientPressed
import javax.inject.Inject

data class ClientsUiState(
    val clients: List<ClientUiModel> = emptyList()
)
sealed class ClientsUiAction {
    object NavigateUp : ClientsUiAction()
    data class OpenClient(val clientId: Int) : ClientsUiAction()
    object OpenAddClient : ClientsUiAction()
}

sealed class ClientsUiEvent {
    object BackPressed : ClientsUiEvent()
    object AddClientPressed : ClientsUiEvent()
    data class EditClientPressed(val clientId: Int) : ClientsUiEvent()
}

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getAllClientsUseCase: GetAllClientsUseCase
) : StateActionsViewModel<ClientsUiState, ClientsUiAction>(ClientsUiState()) {

    init {
        getClients()
    }

    fun handleUiEvent(event: ClientsUiEvent) {
        when (event) {
            is BackPressed -> action(NavigateUp)
            is EditClientPressed -> action(OpenClient(event.clientId))
            is AddClientPressed -> action(OpenAddClient)
        }
    }

    private fun getClients() = viewModelScope.launch(Dispatchers.IO) {
        getAllClientsUseCase().collectLatest { result ->
            if (result.isSuccess) {
                val clients = result.getOrThrow()?.map { it.mapToClientUiModel() }
                clients?.let { updateState { states.value.copy(clients = it) } }
            }
        }
    }
}
