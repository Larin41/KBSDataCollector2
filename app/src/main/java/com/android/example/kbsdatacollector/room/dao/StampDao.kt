package com.android.example.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.room.db.Stamp

@Dao
interface StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stamp: Stamp)

    @Delete
    suspend fun delete(stamp: Stamp)
}