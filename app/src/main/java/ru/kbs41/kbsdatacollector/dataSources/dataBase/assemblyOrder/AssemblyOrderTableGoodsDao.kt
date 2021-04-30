package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts

@Dao
interface AssemblyOrderTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods): Long

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Update
    suspend fun update(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocIdFlow(assemblyOrderId: Long): Flow<List<AssemblyOrderTableGoods>>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocId(assemblyOrderId: Long): List<AssemblyOrderTableGoods>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE id = :id LIMIT 1")
    fun assemblyOrderTableGoodsDao(id: Long): AssemblyOrderTableGoods

    @Query("DELETE FROM assembly_orders_table_goods WHERE assemblyOrderId = :id")
    fun deleteByAssemblyOrderId(id: Long)

    @Transaction
    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :id ORDER BY `row`")
    fun getAssemblyOrderTableGoodsWithProducts(id: Long): Flow<List<AssemblyOrderTableGoodsWithProducts>>

    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoodsWithQtyCollected(query: SupportSQLiteQuery): List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :docId AND productId = :productId")
    fun getTableGoodsByDocAndProduct(docId: Long, productId: Long): List<AssemblyOrderTableGoods>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE id = :id")
    fun getOneTableGoodsById(id: Long): Flow<AssemblyOrderTableGoods>

    @Query("SELECT * FROM assembly_orders_table_goods WHERE id = :id")
    fun getOneRowTableGoodsById(id: Long): AssemblyOrderTableGoods


}