package ru.kbs41.kbsdatacollector.room.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts

class AssemblyOrderFullRepository() {

    val database: AppDatabase = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()
    val stampDao = database.stampDao()
    val rawDao = database.rawDao()
    val productDao = database.productDao()


    fun getProductFlow(id: Long): Flow<Product> {
        return productDao.getProductByIdFlow(id)
    }

    fun getProduct(id: Long): Product {
        return productDao.getProductById(id)
    }

    fun getAssemblyOrderFlow(id: Long): Flow<AssemblyOrder> {
        return assemblyOrderDao.getAssemblyOrderByIdFlow(id)
    }

    fun getAssemblyOrder(id: Long): AssemblyOrder {
        return assemblyOrderDao.getAssemblyOrderById(id)
    }

    fun getAssemblyOrderWithTables(id: Long): Flow<List<AssemblyOrderWithTables>> {
        return assemblyOrderDao.getAssemblyOrderWithTables(id).take(1)
    }

    fun getAssemblyOrderTableGoodsFlow(id: Long): Flow<List<AssemblyOrderTableGoods>> {
        return assemblyOrderTableGoodsDao.getTableGoodsByDocIdFlow(id)
    }

    fun getAssemblyOrderTableGoods(id: Long): List<AssemblyOrderTableGoods> {
        return assemblyOrderTableGoodsDao.getTableGoodsByDocId(id)
    }

    fun getAssemblyOrderTableGoodsWithProducts(id: Long): Flow<List<AssemblyOrderTableGoodsWithProducts>> {
        return assemblyOrderTableGoodsDao.getAssemblyOrderTableGoodsWithProducts(id)
    }

    fun getAssemblyOrderTableStampsByDocIdFlow(id: Long): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdFlow(id)
    }

    fun getAssemblyOrderTableStampsByDocId(id: Long): List<AssemblyOrderTableStamps> {
        return assemblyOrderTableStampsDao.getTableStampsByDocId(id)
    }

    fun getAssemblyOrderTableStampsByDocIdAndProductId(docId: Long, productId: Long): List<AssemblyOrderTableStamps> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductId(docId, productId)
    }

    fun getAssemblyOrderTableStampsByDocIdAndProductIdFlow(docId: Long, productId: Long): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductIdFlow(docId, productId)
    }

    fun getAssemblyOrderTableStampsByAssemblyOrderIdAndProductId(
        docId: Long,
        productId: Long
    ): Flow<List<AssemblyOrderTableStamps>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductIdFlow(docId, productId)
    }

    fun getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(
        docId: Long,
        productId: Long
    ): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdAndProductIdWithProducts(
            docId,
            productId
        )
    }

    fun updateTableGoods(tableGoods: AssemblyOrderTableGoods){
        GlobalScope.launch { assemblyOrderTableGoodsDao.update(tableGoods) }
    }

    fun getOneTableGoodsWithProductById(id: Long): Flow<AssemblyOrderTableGoods> {
        return assemblyOrderTableGoodsDao.getOneTableGoodsById(id)
    }

    fun getOneRowTableGoodsWithProductById(id: Long): AssemblyOrderTableGoods {
        return assemblyOrderTableGoodsDao.getOneRowTableGoodsById(id)
    }

    fun getAssemblyOrderTableStampsByAssemblyOrderIdWithProducts(docId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getTableStampsByDocIdWithProducts(docId)
    }

    fun getAssemblyOrderTableStampsWithProducts(assemblyOrderId: Long): Flow<List<AssemblyOrderTableStampsWithProducts>> {
        return assemblyOrderTableStampsDao.getAssemblyOrderTableStampsWithProducts(assemblyOrderId)
    }

    suspend fun insertAssemblyOrderTableStamps(item: AssemblyOrderTableStamps) {
        assemblyOrderTableStampsDao.insert(item)
    }

    fun getTableGoodsWithStamps(docId: Long): Flow<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>> {

        val queryText: String = """
               SELECT
                    tg.id AS id,
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

    fun getTableGoodsWithStampsByRowId(rowId: Long): Flow<AssemblyOrderTableGoodsWithQtyCollectedAndProducts> {

        val queryText: String = """
               SELECT
                    tg.id AS id,
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

                WHERE tg.id = $rowId

                GROUP BY
                    orderID,
                    productName
                ORDER BY 
                    row
                LIMIT 1
                """

        return rawDao.getTableGoodsByRowId(SimpleSQLiteQuery(queryText))

    }

    fun updateAssemblyOrder(assemblyOrder: AssemblyOrder) {

        GlobalScope.launch { assemblyOrderDao.update(assemblyOrder) }

    }


}