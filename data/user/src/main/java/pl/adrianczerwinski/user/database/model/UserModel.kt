package pl.adrianczerwinski.user.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val company: CompanyModel
)
