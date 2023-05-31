package pl.adrianczerwinski.user.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val name: String,
    val taxNumber: String,
    val accountNo: String,
    val streetAndNumber: String,
    val city: String,
    val postalCode: String,
    val others: Map<String, String>? = null
) : Parcelable
