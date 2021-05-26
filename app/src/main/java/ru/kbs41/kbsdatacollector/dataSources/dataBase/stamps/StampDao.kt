package ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stamp: Stamp)

    @Delete
    suspend fun delete(stamp: Stamp)

    @Query("SELECT * FROM stamps ORDER BY stamp ASC")
    fun getSortedStamps(): Flow<List<Stamp>>

    @Query("SELECT * FROM stamps WHERE :stamp")
    fun getNoteByStamp(stamp: String): List<Stamp>

    @Query("SELECT * FROM stamps WHERE :stamp LIMIT 1")
    fun getOneNoteByStamp(stamp: String): Stamp



}