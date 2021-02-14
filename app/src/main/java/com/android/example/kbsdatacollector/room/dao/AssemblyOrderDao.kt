package com.android.example.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.room.db.AssemblyOrder

@Dao
interface AssemblyOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrder: AssemblyOrder)

    @Delete
    suspend fun delete(assemblyOrder: AssemblyOrder)
}