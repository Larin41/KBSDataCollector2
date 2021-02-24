package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods

@Dao
interface AssemblyOrderTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocId(assemblyOrderId: Long) : Flow<List<AssemblyOrderTableGoods>>

    @Query("DELETE FROM assembly_orders_table_goods WHERE sourceGuid = :sourceGuid")
    fun deleteTableBySourceGuid(sourceGuid: String)

}