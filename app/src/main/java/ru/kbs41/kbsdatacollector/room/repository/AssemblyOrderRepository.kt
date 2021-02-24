package ru.kbs41.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder

class AssemblyOrderRepository(private val assemblyOrderDao: AssemblyOrderDao) {

    val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderDao.getSorted()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrder: AssemblyOrder) {
        assemblyOrderDao.insert(assemblyOrder)
    }

}