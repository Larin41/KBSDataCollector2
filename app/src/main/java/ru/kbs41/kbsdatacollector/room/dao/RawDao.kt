package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps


@Dao
interface RawDao {


    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoods(query: SupportSQLiteQuery): Flow<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoodsByRowId(query: SupportSQLiteQuery): Flow<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>

    @RawQuery()
    fun getTableGoodsForSending(query: SupportSQLiteQuery): List<DataOutgoing.OrderModel.TableGoodsModel>

    @RawQuery()
    fun getTableStampsForSending(query: SupportSQLiteQuery): List<DataOutgoing.OrderModel.TableStampsModel>

    @RawQuery()
    fun getTableStampsByGuidAndProduct(query: SupportSQLiteQuery): List<AssemblyOrderTableStamps>


}