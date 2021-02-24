package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.Stamp

@Dao
interface StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stamp: Stamp)

    @Delete
    suspend fun delete(stamp: Stamp)

    @Query("SELECT * FROM stamps ORDER BY stamp ASC")
    fun getSortedStamps(): Flow<List<Stamp>>

    @Query("SELECT * FROM stamps WHERE :stamp")
    fun getNoteByStamp(stamp: String): List<Stamp>

}