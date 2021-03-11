package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts

@Dao
interface AssemblyOrderTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Update
    suspend fun update(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocIdFlow(assemblyOrderId: Long): Flow<List<AssemblyOrderTableGoods>>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocId(assemblyOrderId: Long): List<AssemblyOrderTableGoods>

    @Query("DELETE FROM assembly_orders_table_goods WHERE assemblyOrderId = :id")
    fun deleteByAssemblyOrderId(id: Long)

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :id ORDER BY `row`")
    fun getAssemblyOrderTableGoodsWithProducts(id: Long): Flow<List<AssemblyOrderTableGoodsWithProducts>>

    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoodsWithQtyCollected(query: SupportSQLiteQuery): List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE id = :id")
    fun getOneTableGoodsById(id: Long): Flow<AssemblyOrderTableGoods>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE id = :id")
    fun getOneRowTableGoodsById(id: Long): AssemblyOrderTableGoods


}