package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import androidx.annotation.WorkerThread
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStampsDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps

class AssemblyOrderTableStampsRepository(private val assemblyOrderTableStampsDao: AssemblyOrderTableStampsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps) {
        assemblyOrderTableStampsDao.insert(assemblyOrderTableStamps)
    }

}