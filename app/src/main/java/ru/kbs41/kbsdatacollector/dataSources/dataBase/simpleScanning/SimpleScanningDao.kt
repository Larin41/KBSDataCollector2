package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.room.*
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpleScanningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(simpleScanning: SimpleScanning)

    @Delete
    suspend fun delete(simpleScanning: SimpleScanning)

    @Query("SELECT * FROM simple_scanning")
    suspend fun getAll(): List<SimpleScanning>

    @Query("SELECT * FROM simple_scanning")
    fun getAllFlow(): Flow<List<SimpleScanning>>

}