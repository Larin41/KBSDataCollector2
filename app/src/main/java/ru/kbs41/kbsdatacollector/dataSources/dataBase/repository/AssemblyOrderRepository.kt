package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder

class AssemblyOrderRepository() {



    val database: AppDatabase = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> =
        assemblyOrderDao.getSortedNotCompletedFlow()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrder: AssemblyOrder) {
        assemblyOrderDao.insert(assemblyOrder)
    }

}