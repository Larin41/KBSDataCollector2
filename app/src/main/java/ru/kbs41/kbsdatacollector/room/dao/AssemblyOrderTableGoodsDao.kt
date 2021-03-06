package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps

@Dao
interface AssemblyOrderTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocId(assemblyOrderId: Long): Flow<List<AssemblyOrderTableGoods>>

    @Query("DELETE FROM assembly_orders_table_goods WHERE assemblyOrderId = :id")
    fun deleteByAssemblyOrderId(id: Long)

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :id ORDER BY `row`")
    fun getAssemblyOrderTableGoodsWithProducts(id: Long): Flow<List<AssemblyOrderTableGoodsWithProducts>>

    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoodsWithQtyCollected(query: SupportSQLiteQuery): List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>


}