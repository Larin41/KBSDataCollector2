package com.android.example.kbsdatacollector.room.dao

import androidx.room.*
import com.android.example.kbsdatacollector.room.db.AssemblyOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface AssemblyOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrder: AssemblyOrder)

    @Delete
    suspend fun delete(assemblyOrder: AssemblyOrder)

    @Query("SELECT * FROM assembly_orders ORDER BY date, number")
    fun getSorted(): Flow<List<AssemblyOrder>>

}