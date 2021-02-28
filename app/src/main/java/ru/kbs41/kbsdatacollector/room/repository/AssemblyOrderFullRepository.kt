package ru.kbs41.kbsdatacollector.room.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps

class AssemblyOrderFullRepository() {

    val database: AppDatabase = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()

    fun getAssemblyOrder(id: Long): Flow<List<AssemblyOrder>> {
        return assemblyOrderDao.getAssemblyOrderById(id).take(0)
    }

    fun getAssemblyOrderTableGoods(id: Long): Flow<List<AssemblyOrderTableGoods>> {
        return assemblyOrderTableGoodsDao.getTableGoodsByDocId(id)
    }

    fun getAssemblyOrderTableStamps(id: Long): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocId(id)
    }


}