package com.android.example.kbsdatacollector.room.dao

import androidx.room.*
import com.android.example.kbsdatacollector.room.db.AssemblyOrderTableGoods
import kotlinx.coroutines.flow.Flow

@Dao
interface AssemblyOrderTableGoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Query("SELECT * FROM assembly_orders_table_goods WHERE assemblyOrderId = :assemblyOrderId ORDER BY `row`")
    fun getTableGoodsByDocId(assemblyOrderId: Long) : Flow<List<AssemblyOrderTableGoods>>

}