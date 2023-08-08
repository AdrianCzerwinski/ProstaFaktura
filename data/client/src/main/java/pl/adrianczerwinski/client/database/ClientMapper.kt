package pl.adrianczerwinski.client.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.adrianczerwinski.client.database.model.ClientModel
import pl.adrianczerwinski.client.model.Client

class ClientsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToMap(value: String?): Map<String, String>? {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMapToString(map: Map<String, String>?): String? = gson.toJson(map)

    @TypeConverter
    fun fromStringsListToString(list: List<String>?): String? = list?.let { gson.toJson(list) }

    @TypeConverter
    fun fromStringToListOfStrings(value: String): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}
internal fun ClientModel.toClient() = Client(
    id = this.id,
    name = this.name,
    taxNumber = this.taxNumber,
    streetAndNumber = this.streetAndNumber,
    city = this.city,
    postalCode = this.postalCode,
    emails = this.emails,
    others = this.others,
    language = this.language,
    currency = this.currency,
    country = this.country
)

internal fun Client.toModel() = ClientModel(
    name = this.name,
    taxNumber = this.taxNumber,
    streetAndNumber = this.streetAndNumber,
    city = this.city,
    postalCode = this.postalCode,
    emails = this.emails,
    others = this.others,
    language = this.language,
    currency = this.currency,
    country = this.country
)
