package pl.adrianczerwinski.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.onboarding.OnboardingUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.OnboardingUiEvent.ButtonPressed
import pl.adrianczerwinski.prostafaktura.features.onboarding.R
import javax.inject.Inject

private val onboardingPages = listOf(
    PageUiModel(
        pageImage = R.drawable.onboarding_1,
        text = R.string.onboarding_text_1
    ),
    PageUiModel(
        pageImage = R.drawable.onboarding_2,
        text = R.string.onboarding_text_2
    ),
    PageUiModel(
        pageImage = R.drawable.onboarding_3,
        text = R.string.onboarding_text_3
    )
)

data class OnboardingUiState(
    val pages: List<PageUiModel> = onboardingPages
)

sealed class OnboardingUiAction {
    object OpenSignIn : OnboardingUiAction()
}

sealed class OnboardingUiEvent {
    object ButtonPressed : OnboardingUiEvent()
}

@HiltViewModel
class OnboardingViewModel @Inject constructor() : StateActionsViewModel<OnboardingUiState, OnboardingUiAction>(OnboardingUiState()) {

    fun handleUiEvent(event: OnboardingUiEvent) {
        when (event) {
            is ButtonPressed -> action(OpenSignIn)
        }
    }
}
