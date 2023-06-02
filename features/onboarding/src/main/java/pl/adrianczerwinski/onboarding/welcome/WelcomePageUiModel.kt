package pl.adrianczerwinski.onboarding.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WelcomePageUiModel(
    @DrawableRes val pageImage: Int,
    @StringRes val text: Int
)
