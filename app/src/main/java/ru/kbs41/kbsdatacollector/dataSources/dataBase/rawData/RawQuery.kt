package ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing

object RawQuery {

    val database = App().database
    val rawDao = database.rawDao()

    fun getTableGoodsWithStamps(docId: Long): Flow<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>> {

        val queryText: String = """
               SELECT
                    tg.id AS id,
                    tg.row AS row,
                    tg.assemblyOrderId AS orderID,
                    pr.name AS productName,
                    pr.id AS productId,
                    pr.hasStamp AS productHasStamps,
                    tg.qty AS qty,
                    CASE pr.hasStamp
                        WHEN 1
                            THEN IFNULL(COUNT(ts.barcode),0) 	
                        ELSE tg.qtyCollected
                    END qtyCollected
                    
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

    fun getTableGoodsForSending(docId: Long): List<DataOutgoing.OrderModel.TableGoodsModel> {

        val queryText: String = """
                SELECT
                    pr.name productName,
                    pr.guid productGuid,
                    tg.qty qty,
                    tg.qtyCollected qtyCollected
                FROM 
                    assembly_orders_table_goods tg
                    
                    LEFT JOIN products pr
                    ON tg.productId = pr.id
                    
                WHERE
                    assemblyOrderId = $docId
                """

        return rawDao.getTableGoodsForSending(SimpleSQLiteQuery(queryText))
    }

    fun getTableStampsForSending(docId: Long): List<DataOutgoing.OrderModel.TableStampsModel> {

        val queryText: String = """
                SELECT
                    pr.name productName,
                    pr.guid productGuid,
                    ts.barcode stamp
                FROM 
                    assembly_orders_table_stamps ts
                    
                    LEFT JOIN products pr
                    ON ts.productId = pr.id
                    
                WHERE
                    ts.assemblyOrderId = $docId
                """

        return rawDao.getTableStampsForSending(SimpleSQLiteQuery(queryText))
    }

    fun getTableStampsByGuidAndProduct(guid: String, productId: Long): List<AssemblyOrderTableStamps?> {

        val queryText: String = """
                SELECT
                    *
                FROM
                    assembly_orders_table_stamps
                WHERE 
                    productId = $productId AND
                    assemblyOrderId IN (SELECT
                                            id
                                        FROM
                                            assembly_orders
                                        WHERE
                                            guid = """" + guid + """")
                """

        return rawDao.getTableStampsByGuidAndProduct(SimpleSQLiteQuery(queryText))
    }
}