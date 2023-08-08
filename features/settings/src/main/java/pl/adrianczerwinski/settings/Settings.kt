package pl.adrianczerwinski.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.prostafaktura.features.settings.R
import pl.adrianczerwinski.settings.SettingsUiAction.NavigateUp
import pl.adrianczerwinski.settings.SettingsUiAction.OpenClientsList
import pl.adrianczerwinski.settings.SettingsUiEvent.BackPressed
import pl.adrianczerwinski.settings.SettingsUiEvent.ClientsPressed
import pl.adrianczerwinski.ui.IconListItemRow
import pl.adrianczerwinski.ui.LightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.AppTopBar
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

@Composable
fun Settings(
    navigation: SettingsFeatureNavigation,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    SettingsScreen(viewModel::handleUiEvent)

    HandleAction(viewModel.actions) { action ->
        when (action) {
            NavigateUp -> navigation.navigateUp()
            OpenClientsList -> navigation.openClientsList()
        }
    }

}

@Composable
private fun SettingsScreen(
    uiEvent: (SettingsUiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = { AppTopBar(title = stringResource(R.string.settings)) { uiEvent(BackPressed) } }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconListItemRow(
                title = stringResource(R.string.your_data),
                actionTitle = stringResource(R.string.edit),
                actionIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = uiR.drawable.ic_edit),
                        contentDescription = "Edit Icon",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                }
            ) {}

            IconListItemRow(
                title = stringResource(R.string.clients),
                actionTitle = stringResource(R.string.go)
            ) { uiEvent(ClientsPressed) }
        }
    }
}

@LightDarkPreview
@Composable
private fun SettingsScreenPreview() = ScreenPreview { SettingsScreen() }
