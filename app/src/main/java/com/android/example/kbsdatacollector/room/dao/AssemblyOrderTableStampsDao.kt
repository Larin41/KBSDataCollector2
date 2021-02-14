package com.android.example.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.room.db.AssemblyOrderTableStamps

@Dao
interface AssemblyOrderTableStampsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Delete
    suspend fun delete(assemblyOrderTableStamps: AssemblyOrderTableStamps)

}