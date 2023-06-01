package pl.adrianczerwinski.ui.models

data class TextFieldState(
    val value: String = "",
    val isEnabled: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
