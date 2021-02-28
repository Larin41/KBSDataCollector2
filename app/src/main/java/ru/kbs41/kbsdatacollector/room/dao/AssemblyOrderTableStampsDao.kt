package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps

@Dao
interface AssemblyOrderTableStampsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Delete
    suspend fun delete(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId ORDER BY id")
    fun getTableStampsByDocId(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStamps>>

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId AND ProductId = :productId ORDER BY id")
    fun getTableStampsByDocIdAndProductId(assemblyOrderId: Long, productId: Long): Flow<List<AssemblyOrderTableStamps>>

}