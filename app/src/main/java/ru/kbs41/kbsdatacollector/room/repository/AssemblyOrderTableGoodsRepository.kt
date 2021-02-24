package ru.kbs41.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderTableGoodsDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods

class AssemblyOrderTableGoodsRepository(private val assemblyOrderTableGoodsDao: AssemblyOrderTableGoodsDao) {

    //val allSortedAssemblyOrders: Flow<List<AssemblyOrder>> = assemblyOrderTableGoodsDao.getTableGoodsByDocId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods) {
        assemblyOrderTableGoodsDao.insert(assemblyOrderTableGoods)
    }

}