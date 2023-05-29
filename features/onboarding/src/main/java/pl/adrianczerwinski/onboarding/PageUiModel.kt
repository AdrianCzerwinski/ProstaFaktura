package pl.adrianczerwinski.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PageUiModel(
    @DrawableRes val pageImage: Int,
    @StringRes val text: Int
)
