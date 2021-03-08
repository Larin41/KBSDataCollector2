package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.Settings

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Delete
    suspend fun delete(settings: Settings)

    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<Settings>>

    @Query("SELECT * FROM settings WHERE isCurrent = 1 LIMIT 1")
    fun getCurrentSettings(): Flow<Settings>

}