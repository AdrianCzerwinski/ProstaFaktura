package pl.adrianczerwinski.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface StateHandler<UiState> {
    val states: StateFlow<UiState>
    fun setState(state: UiState)
    fun updateState(block: UiState.() -> UiState)
}

interface ActionsHandler<UiAction> {
    val actions: SharedFlow<UiAction>
    suspend fun emitAction(action: UiAction)
}

private class StateHandlerImpl<UiState>(defaultState: UiState) : StateHandler<UiState> {
    private val _state = MutableStateFlow(defaultState)
    override val states: StateFlow<UiState> = _state.asStateFlow()

    override fun setState(state: UiState) {
        _state.value = state
    }

    override fun updateState(block: UiState.() -> UiState) {
        _state.value = block(_state.value)
    }
}

private class ActionsHandlerImpl<UiAction> : ActionsHandler<UiAction> {
    private val _actions = MutableSharedFlow<UiAction>()
    override val actions: SharedFlow<UiAction> = _actions.asSharedFlow()

    override suspend fun emitAction(action: UiAction) {
        _actions.emit(action)
    }
}

open class StateViewModel<UiState>(defaultState: UiState) :
    ViewModel(),
    StateHandler<UiState> by StateHandlerImpl(defaultState)

open class ActionsViewModel<UiAction> :
    ViewModel(),
    ActionsHandler<UiAction> by ActionsHandlerImpl()

open class StateActionsViewModel<UiState, UiAction>(defaultState: UiState) :
    ActionsViewModel<UiAction>(),
    StateHandler<UiState> by StateHandlerImpl(defaultState)

fun <UiAction> ActionsViewModel<UiAction>.action(action: UiAction) = viewModelScope.launch {
    emitAction(action)
}
