package pl.adrianczerwinski.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
data class ClientModel(
    @PrimaryKey
    val taxNumber: String,
    val name: String,
    val streetAndNumber: String,
    val city: String,
    val postalCode: String,
    val emails: List<String>? = null,
    val phoneNumber: String,
    val others: Map<String, String>? = null,
    val language: String,
    val currency: String,
    val country: String
)
