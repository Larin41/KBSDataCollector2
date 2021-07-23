package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssemblyOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrder: AssemblyOrder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(assemblyOrder: AssemblyOrder)

    @Delete
    suspend fun delete(assemblyOrder: AssemblyOrder)

    @Query("DELETE FROM assembly_orders WHERE id = :id")
    fun deleteByAssemblyOrderId(id: Long)

    @Query("SELECT * FROM assembly_orders ORDER BY date DESC, number DESC")
    fun getSortedFlow(): Flow<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE isCompleted = 0 ORDER BY date DESC, number DESC")
    fun getSortedNotCompletedFlow(): Flow<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE id = :id LIMIT 1")
    fun getAssemblyOrderByIdFlow(id: Long): Flow<AssemblyOrder>

    @Query("SELECT * FROM assembly_orders WHERE id = :id LIMIT 1")
    fun getAssemblyOrderById(id: Long): AssemblyOrder

    @Query("SELECT * FROM assembly_orders WHERE guid = :guid LIMIT 1")
    fun getAssemblyOrderByGuid(guid: String): AssemblyOrder?

    @Transaction
    @Query("SELECT * FROM ASSEMBLY_ORDERS WHERE :id LIMIT 1")
    fun getAssemblyOrderWithTables(id: Long): Flow<List<AssemblyOrderWithTables>>

    @Query("SELECT * FROM assembly_orders WHERE isCompleted = 1 AND isSent = 0")
    fun getAssemblyOrderCompleteNotSentFlow(): Flow<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE isCompleted = 1 AND isSent = 0")
    fun getCompletedNotSendedLiveData(): LiveData<List<AssemblyOrder>>

    @Query("SELECT * FROM assembly_orders WHERE isCompleted = 1 AND isSent = 0")
    fun getAssemblyOrderCompleteNotSent(): List<AssemblyOrder>



}