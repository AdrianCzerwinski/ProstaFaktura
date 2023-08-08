package pl.adrianczerwinski.client.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    val id: Int = 0,
    val name: String,
    val taxNumber: String,
    val streetAndNumber: String,
    val city: String,
    val postalCode: String,
    val emails: List<String>? = null,
    val others: Map<String, String>? = null,
    val language: String,
    val currency: String,
    val country: String
) : Parcelable
