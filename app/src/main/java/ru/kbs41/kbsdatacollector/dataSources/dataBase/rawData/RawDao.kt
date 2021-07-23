package ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps


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

    @RawQuery(observedEntities = [AssemblyOrder::class])
    fun getNotCompletedAssemblyOrders(query: SupportSQLiteQuery): Flow<List<AssemblyOrdersWithContractors>>


}