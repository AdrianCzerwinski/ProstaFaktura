package pl.adrianczerwinski.user.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import pl.adrianczerwinski.user.database.model.CompanyModel
import pl.adrianczerwinski.user.database.model.UserModel
import pl.adrianczerwinski.user.model.Company
import pl.adrianczerwinski.user.model.User

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCompanyModel(company: CompanyModel): String {
        return gson.toJson(company)
    }

    @TypeConverter
    fun toCompanyModel(companyString: String): CompanyModel {
        return gson.fromJson(companyString, CompanyModel::class.java)
    }
}

internal fun UserModel.toModel() = User(
    name = this.name,
    company = this.company.toModel(),
    phoneNumber = this.phoneNumber,
    email = this.email
)

internal fun CompanyModel.toModel() = Company(
    name = this.name,
    taxNumber = this.taxNumber,
    accountNumber = this.accountNo,
    streetAndNumber = this.streetAndNumber,
    city = this.city,
    postalCode = this.postalCode
)

internal fun User.toModel() = UserModel(
    id = 1,
    name = this.name,
    company = this.company.toModel(),
    phoneNumber = this.phoneNumber,
    email = this.email
)

internal fun Company.toModel() = CompanyModel(
    name = this.name,
    taxNumber = this.taxNumber,
    accountNo = this.accountNumber,
    streetAndNumber = this.streetAndNumber,
    city = this.city,
    postalCode = this.postalCode
)
