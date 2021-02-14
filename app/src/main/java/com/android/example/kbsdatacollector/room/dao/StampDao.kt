package com.android.example.kbsdatacollector.room.dao

import androidx.room.*
import com.android.example.kbsdatacollector.room.db.Barcode
import com.android.example.kbsdatacollector.room.db.Stamp
import kotlinx.coroutines.flow.Flow

@Dao
interface StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stamp: Stamp)

    @Delete
    suspend fun delete(stamp: Stamp)

    @Query("SELECT * FROM stamps ORDER BY stamp ASC")
    fun getSortedStamps(): Flow<List<Stamp>>

}