package com.android.example.kbsdatacollector.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.db.Product

@Dao
interface AssemblyOrder {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrder: AssemblyOrder)

    @Delete
    suspend fun delete(assemblyOrder: AssemblyOrder)
}