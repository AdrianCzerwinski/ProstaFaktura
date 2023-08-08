package pl.adrianczerwinski.settings.clients

import pl.adrianczerwinski.client.model.Client

fun Client.mapToClientUiModel() = with(this) {
    ClientUiModel(
        id = id,
        name = name,
        taxNumber = taxNumber.ifEmpty { null }
    )
}
