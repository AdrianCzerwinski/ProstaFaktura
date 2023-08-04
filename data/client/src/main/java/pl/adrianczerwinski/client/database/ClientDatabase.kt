package pl.adrianczerwinski.client.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.adrianczerwinski.client.database.model.ClientModel

@Database(entities = [ClientModel::class], version = 1)
@TypeConverters(ClientsConverter::class)
abstract class ClientDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
}
