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
    val productDao = database.productDao()


    fun getProduct(id: Long): Flow<List<Product>> {
        return productDao.getProductById(id).take(1)
    }

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

    fun getAssemblyOrderTableStampsByAssemblyOrderIdAndProductId(docId: Long, productId: Long): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductId(docId, productId)
    }

    fun getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(docId: Long, productId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductIdWithProducts(docId, productId)
    }

    fun getAssemblyOrderTableStampsWithProducts(id: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getAssemblyOrderTableStampsWithProducts(id)
    }

    suspend fun insertAssemblyOrderTableStamps(item: AssemblyOrderTableStamps){
        assemblyOrderTableStampsDao.insert(item)
    }

}