package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import androidx.annotation.WorkerThread
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoodsDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods

class AssemblyOrderTableGoodsRepository(private val assemblyOrderTableGoodsDao: AssemblyOrderTableGoodsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods) {
        assemblyOrderTableGoodsDao.insert(assemblyOrderTableGoods)
    }

}