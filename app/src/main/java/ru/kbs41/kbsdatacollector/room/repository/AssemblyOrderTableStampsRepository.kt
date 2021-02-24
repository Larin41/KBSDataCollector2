package ru.kbs41.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderTableStampsDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps

class AssemblyOrderTableStampsRepository(private val assemblyOrderTableStampsDao: AssemblyOrderTableStampsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps) {
        assemblyOrderTableStampsDao.insert(assemblyOrderTableStamps)
    }

}