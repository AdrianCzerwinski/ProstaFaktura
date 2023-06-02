package pl.adrianczerwinski.user.model

data class User(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val company: Company
)
