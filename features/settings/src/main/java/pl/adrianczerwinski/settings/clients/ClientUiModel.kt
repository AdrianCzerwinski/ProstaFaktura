package pl.adrianczerwinski.settings.clients

data class ClientUiModel(
    val id: Int,
    val name: String,
    val taxNumber: String? = null
)
