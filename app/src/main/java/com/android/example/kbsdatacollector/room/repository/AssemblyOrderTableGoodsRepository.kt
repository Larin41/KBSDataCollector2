package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.AssemblyOrderTableGoodsDao
import com.android.example.kbsdatacollector.room.db.AssemblyOrderTableGoods

class AssemblyOrderTableGoodsRepository(private val assemblyOrderTableGoodsDao: AssemblyOrderTableGoodsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods) {
        assemblyOrderTableGoodsDao.insert(assemblyOrderTableGoods)
    }

}