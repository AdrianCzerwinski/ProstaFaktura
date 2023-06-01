package pl.adrianczerwinski.onboarding.welcome

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.domain.user.GetUserUseCase
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiAction.OpenMain
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiEvent.ButtonPressed
import pl.adrianczerwinski.prostafaktura.features.onboarding.R
import javax.inject.Inject

private val onboardingPages = listOf(
    WelcomePageUiModel(
        pageImage = R.drawable.onboarding_1,
        text = R.string.onboarding_text_1
    ),
    WelcomePageUiModel(
        pageImage = R.drawable.onboarding_2,
        text = R.string.onboarding_text_2
    ),
    WelcomePageUiModel(
        pageImage = R.drawable.onboarding_3,
        text = R.string.onboarding_text_3
    )
)

data class WelcomeUiState(
    val pages: List<WelcomePageUiModel> = onboardingPages
)

sealed class WelcomeUiAction {
    object OpenSignIn : WelcomeUiAction()
    object OpenMain : WelcomeUiAction()
}

sealed class WelcomeUiEvent {
    object ButtonPressed : WelcomeUiEvent()
}

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : StateActionsViewModel<WelcomeUiState, WelcomeUiAction>(WelcomeUiState()) {

    fun handleUiEvent(event: WelcomeUiEvent) {
        when (event) {
            is ButtonPressed -> onButtonPressed()
        }
    }

    private fun onButtonPressed() = viewModelScope.launch {
        getUserUseCase().collectLatest { result ->
            if (result.isSuccess) {
                val user = result.getOrNull()
                user?.let { action(OpenMain) } ?: action(OpenSignIn)
            } else {
                action(OpenSignIn)
            }
        }
    }
}
