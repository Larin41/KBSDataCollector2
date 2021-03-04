package ru.kbs41.kbsdatacollector.room.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.db.*

class AssemblyOrderFullRepository() {

    val database: AppDatabase = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()
    val stampDao = database.stampDao()

    fun getAssemblyOrder(id: Long): Flow<List<AssemblyOrder>> {
        return assemblyOrderDao.getAssemblyOrderById(id).take(1)
    }

    fun getAssemblyOrderWithTables(id: Long): Flow<List<AssemblyOrderWithTables>> {
        return assemblyOrderDao.getAssemblyOrderWithTables(id).take(1)
    }

    fun getAssemblyOrderTableGoods(id: Long): Flow<List<AssemblyOrderTableGoods>> {
        return assemblyOrderTableGoodsDao.getTableGoodsByDocId(id)
    }

    fun getAssemblyOrderTableGoodsWithProducts(id: Long): Flow<List<AssemblyOrderTableGoodsWithProducts>> {
        return assemblyOrderTableGoodsDao.getAssemblyOrderTableGoodsWithProducts(id)
    }

    fun getAssemblyOrderTableStamps(id: Long): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocId(id)
    }

    fun getAssemblyOrderTableStampsWithProducts(id: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getAssemblyOrderTableStampsWithProducts(id)
    }

    suspend fun insertStamp(stamp: Stamp){
        stampDao.insert(stamp)
    }

}