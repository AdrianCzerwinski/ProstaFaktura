package pl.adrianczerwinski.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.adrianczerwinski.common.ActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.settings.SettingsUiAction.NavigateUp
import pl.adrianczerwinski.settings.SettingsUiAction.OpenClientsList
import pl.adrianczerwinski.settings.SettingsUiEvent.BackPressed
import pl.adrianczerwinski.settings.SettingsUiEvent.ClientsPressed
import javax.inject.Inject

sealed class SettingsUiAction {
    object NavigateUp : SettingsUiAction()
    object OpenClientsList : SettingsUiAction()
}

sealed class SettingsUiEvent {
    object BackPressed : SettingsUiEvent()
    object ClientsPressed : SettingsUiEvent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor() : ActionsViewModel<SettingsUiAction>() {

    fun handleUiEvent(event: SettingsUiEvent) {
        when (event) {
            BackPressed -> action(NavigateUp)
            ClientsPressed -> action(OpenClientsList)
        }
    }
}
