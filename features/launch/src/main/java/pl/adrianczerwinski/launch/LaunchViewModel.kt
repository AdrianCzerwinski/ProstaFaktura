package pl.adrianczerwinski.launch

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.adrianczerwinski.common.ActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.domain.user.GetOnboardingShownUseCase
import pl.adrianczerwinski.launch.LaunchUiAction.OpenMainScreen
import pl.adrianczerwinski.launch.LaunchUiAction.OpenOnboarding
import javax.inject.Inject

sealed class LaunchUiAction {
    object OpenMainScreen : LaunchUiAction()
    object OpenOnboarding : LaunchUiAction()
}
@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val getOnboardingShownUseCase: GetOnboardingShownUseCase
) : ActionsViewModel<LaunchUiAction>() {

    init {
        getIsUserRegistered()
    }

    private fun getIsUserRegistered() = viewModelScope.launch {
        getOnboardingShownUseCase().collectLatest { result ->
            delay(LOADER_DELAY)
            if (result.isSuccess) {
                if (result.getOrThrow() == true) action(OpenMainScreen) else action(OpenOnboarding)
            } else {
                action(OpenOnboarding)
            }
        }
    }
}

private const val LOADER_DELAY = 1000L
