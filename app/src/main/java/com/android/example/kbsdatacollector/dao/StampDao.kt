package com.android.example.kbsdatacollector.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.db.Stamp

interface StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stamp: Stamp)

    @Delete
    suspend fun delete(stamp: Stamp)
}