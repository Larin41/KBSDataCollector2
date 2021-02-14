package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.AssemblyOrderTableStampsDao
import com.android.example.kbsdatacollector.room.db.AssemblyOrderTableStamps

class AssemblyOrderTableStampsRepository(private val assemblyOrderTableStampsDao: AssemblyOrderTableStampsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableStamps: AssemblyOrderTableStamps) {
        assemblyOrderTableStampsDao.insert(assemblyOrderTableStamps)
    }

}