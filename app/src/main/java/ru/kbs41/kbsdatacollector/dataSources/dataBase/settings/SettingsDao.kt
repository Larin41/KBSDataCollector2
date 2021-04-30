package ru.kbs41.kbsdatacollector.dataSources.dataBase.settings

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Delete
    suspend fun delete(settings: Settings)

    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<Settings>>

    @Query("SELECT * FROM settings WHERE id = 1 LIMIT 1")
    fun getCurrentSettings(): Settings

}