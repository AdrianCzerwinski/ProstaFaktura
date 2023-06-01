package pl.adrianczerwinski.user.database.model

data class CompanyModel(
    val name: String,
    val taxNumber: String,
    val accountNo: String,
    val streetAndNumber: String,
    val city: String,
    val postalCode: String,
    val others: Map<String, String>? = null
)
