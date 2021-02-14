package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.AssemblyOrderDao
import com.android.example.kbsdatacollector.room.dao.ProductDao
import com.android.example.kbsdatacollector.room.db.AssemblyOrder
import com.android.example.kbsdatacollector.room.db.Product
import kotlinx.coroutines.flow.Flow

class AssemblyOrderRepository(private val assemblyOrderDao: AssemblyOrderDao) {

    val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderDao.getSorted()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrder: AssemblyOrder) {
        assemblyOrderDao.insert(assemblyOrder)
    }

}