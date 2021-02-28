package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder

@Dao
interface AssemblyOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrder: AssemblyOrder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(assemblyOrder: AssemblyOrder)

    @Delete
    suspend fun delete(assemblyOrder: AssemblyOrder)

    @Query("SELECT * FROM assembly_orders ORDER BY date DESC, number DESC")
    fun getSorted(): Flow<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE id = :id")
    fun getAssemblyOrderById(id: Long): Flow<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE guid = :guid")
    fun getAssemblyOrderByGuid(guid: String): List<AssemblyOrder>

}