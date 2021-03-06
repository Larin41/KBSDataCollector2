package ru.kbs41.kbsdatacollector.room.repository

import androidx.sqlite.db.SimpleSQLiteQuery
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
    val rawDao = database.rawDao()
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

    fun getAssemblyOrderTableStampsByAssemblyOrderIdWithProducts(docId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdWithProducts(docId)
    }

    fun getAssemblyOrderTableStampsWithProducts(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getAssemblyOrderTableStampsWithProducts(assemblyOrderId)
    }

    suspend fun insertAssemblyOrderTableStamps(item: AssemblyOrderTableStamps){
        assemblyOrderTableStampsDao.insert(item)
    }

    fun getTest(docId: Long): Flow<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>> {

        val queryText: String = """
               SELECT
                    tg.row AS row,
                    tg.assemblyOrderId AS orderID,
                    pr.name AS productName,
                    pr.id AS productId,
                    tg.qty AS qty,
                    IFNULL(COUNT(ts.barcode),0) AS qtyCollected
                FROM
                    assembly_orders_table_goods tg        
                    
                    LEFT JOIN products pr
                    ON tg.productId = pr.id
                    
                    LEFT JOIN assembly_orders_table_stamps ts
                    ON tg.productId = ts.productId AND tg.assemblyOrderId = ts.assemblyOrderId

                WHERE tg.assemblyOrderId = $docId

                GROUP BY
                    orderID,
                    productName
                ORDER BY 
                    row
                """

        return rawDao.getTableGoods(SimpleSQLiteQuery(queryText))

    }


}