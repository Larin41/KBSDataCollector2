package ru.kbs41.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder

class AssemblyOrderRepository(_assemblyOrderDao: AssemblyOrderDao) {


    val assemblyOrderDao = _assemblyOrderDao
    val database: AppDatabase = App().database
    val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> =
        assemblyOrderDao.getSortedNotCompletedFlow()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrder: AssemblyOrder) {
        assemblyOrderDao.insert(assemblyOrder)
    }

}