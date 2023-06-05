package pl.adrianczerwinski.main

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.main.MainUiAction.OpenNewClient
import pl.adrianczerwinski.main.MainUiAction.OpenNewInvoice
import pl.adrianczerwinski.main.MainUiAction.OpenSettings
import pl.adrianczerwinski.main.MainUiEvent.AddButtonPressed
import pl.adrianczerwinski.main.MainUiEvent.AddClientPressed
import pl.adrianczerwinski.main.MainUiEvent.CloseBottomSheet
import pl.adrianczerwinski.main.MainUiEvent.CreateInvoicePressed
import pl.adrianczerwinski.main.MainUiEvent.SettingsPressed
import javax.inject.Inject

data class MainUiState(
    val showBottomSheetPicker: Boolean = false
)

sealed class MainUiAction {
    object OpenNewInvoice : MainUiAction()
    object OpenSettings : MainUiAction()
    object OpenNewClient : MainUiAction()
}

sealed class MainUiEvent {
    object AddButtonPressed : MainUiEvent()
    object CreateInvoicePressed : MainUiEvent()
    object AddClientPressed : MainUiEvent()
    object SettingsPressed : MainUiEvent()
    object CloseBottomSheet : MainUiEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor() : StateActionsViewModel<MainUiState, MainUiAction>(MainUiState()) {

    fun handleUiEvent(event: MainUiEvent) {
        when (event) {
            AddButtonPressed -> updateState { copy(showBottomSheetPicker = true) }
            AddClientPressed -> onAddClientPressed()
            CloseBottomSheet -> updateState { copy(showBottomSheetPicker = false) }
            CreateInvoicePressed -> onCreateInvoicePressed()
            SettingsPressed -> action(OpenSettings)
        }
    }

    private fun onAddClientPressed() {
        updateState { copy(showBottomSheetPicker = false) }
        action(OpenNewClient)
    }

    private fun onCreateInvoicePressed() {
        updateState { copy(showBottomSheetPicker = false) }
        action(OpenNewInvoice)
    }
}
