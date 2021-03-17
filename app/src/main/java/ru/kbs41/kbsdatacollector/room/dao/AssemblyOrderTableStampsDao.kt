package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts

@Dao
interface AssemblyOrderTableStampsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Delete
    suspend fun delete(assemblyOrderTableStamps: AssemblyOrderTableStamps)

    @Query ("DELETE FROM assembly_orders_table_stamps WHERE id = :id" )
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId ORDER BY id")
    fun getTableStampsByDocIdFlow(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStamps>>

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId ORDER BY id")
    fun getTableStampsByDocId(assemblyOrderId: Long): List<AssemblyOrderTableStamps>

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE barcode = :barcode LIMIT 1")
    fun getTableStampsByBarcode(barcode: String): AssemblyOrderTableStamps

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId AND productId = :productId ORDER BY id")
    fun getTableStampsByDocIdAndProductId(assemblyOrderId: Long, productId: Long): List<AssemblyOrderTableStamps>

    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId AND ProductId = :productId ORDER BY id")
    fun getTableStampsByDocIdAndProductIdFlow(assemblyOrderId: Long, productId: Long): Flow<List<AssemblyOrderTableStamps>>

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId ORDER BY id")
    fun getAssemblyOrderTableStampsWithProducts(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>>

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId AND ProductId = :productId ORDER BY id")
    fun getTableStampsByDocIdAndProductIdWithProducts(assemblyOrderId: Long, productId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>>

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_stamps WHERE assemblyOrderId = :assemblyOrderId ORDER BY id")
    fun getTableStampsByDocIdWithProducts(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>>


}