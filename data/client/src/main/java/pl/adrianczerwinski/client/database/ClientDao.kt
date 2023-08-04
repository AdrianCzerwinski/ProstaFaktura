package pl.adrianczerwinski.client.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.client.database.model.ClientModel

@Dao
interface ClientDao {

    @Query("SELECT * FROM client WHERE client.taxNumber = :taxNumber")
    fun getClient(taxNumber: String): Flow<ClientModel>

    @Query("SELECT * FROM client")
    fun getAllClients(): Flow<List<ClientModel>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveClient(client: ClientModel)
}
