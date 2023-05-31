package pl.adrianczerwinski.onboarding.welcome

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.onboarding.welcome.OnboardingUiAction.OpenSignIn
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

sealed class OnboardingUiAction {
    object OpenSignIn : OnboardingUiAction()
}

sealed class WelcomeUiEvent {
    object ButtonPressed : WelcomeUiEvent()
}

@HiltViewModel
class WelcomeViewModel @Inject constructor() : StateActionsViewModel<WelcomeUiState, OnboardingUiAction>(WelcomeUiState()) {

    fun handleUiEvent(event: WelcomeUiEvent) {
        when (event) {
            is ButtonPressed -> action(OpenSignIn)
        }
    }
}
