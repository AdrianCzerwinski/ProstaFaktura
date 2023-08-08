package pl.adrianczerwinski.settings.clients

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.prostafaktura.features.settings.R
import pl.adrianczerwinski.settings.SettingsFeatureNavigation
import pl.adrianczerwinski.settings.clients.ClientsUiAction.NavigateUp
import pl.adrianczerwinski.settings.clients.ClientsUiAction.OpenAddClient
import pl.adrianczerwinski.settings.clients.ClientsUiAction.OpenClient
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.AddClientPressed
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.BackPressed
import pl.adrianczerwinski.settings.clients.ClientsUiEvent.EditClientPressed
import pl.adrianczerwinski.ui.IconListItemRow
import pl.adrianczerwinski.ui.LightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.AppTopBar
import pl.adrianczerwinski.ui.components.BottomBarWithButton
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

@Composable
fun Clients(
    navigation: SettingsFeatureNavigation,
    viewModel: ClientsViewModel = hiltViewModel()
) {
    val uiState = viewModel.states.collectAsStateWithLifecycle().value
    ClientsScreen(uiState, viewModel::handleUiEvent)

    HandleAction(viewModel.actions) { action ->
        when (action) {
            is NavigateUp -> navigation.navigateUp()
            is OpenClient -> {
                // TODO navigate to edit client screen
            }
            is OpenAddClient -> navigation.openAddClient()
        }
    }

}

@Composable
private fun ClientsScreen(
    uiState: ClientsUiState,
    uiEvent: (ClientsUiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = { AppTopBar(title = stringResource(R.string.clients)) { uiEvent(BackPressed) } },
        bottomBar = {
            BottomBarWithButton(buttonText = stringResource(id = R.string.add_client)) { uiEvent(AddClientPressed) }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.clients.size) {index ->
                IconListItemRow(
                    title = uiState.clients[index].name ,
                    actionTitle = stringResource(R.string.edit),
                    actionIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = uiR.drawable.ic_edit),
                            contentDescription = "Edit Icon",
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    },
                    leadIcon = painterResource(id = R.drawable.ic_client_files),
                    subTitle = uiState.clients[index].taxNumber
                ) { uiEvent(EditClientPressed(uiState.clients[index].id)) }
            }
        }
    }
}

@LightDarkPreview
@Composable
private fun ClientsScreenPreview() = ScreenPreview { ClientsScreen(ClientsUiState()) }
