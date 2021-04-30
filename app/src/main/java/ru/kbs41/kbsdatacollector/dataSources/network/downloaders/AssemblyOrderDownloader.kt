package ru.kbs41.kbsdatacollector.dataSources.network.downloaders

import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData.RawQuery
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.IncomeDataOrders

class AssemblyOrderDownloader {

    val database = App().database

    val productDao = database.productDao()
    val assemblyOrderDao = database.assemblyOrderDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()

    suspend fun downloadDocuments(orders: List<IncomeDataOrders.Order>?) {
        orders?.forEach {
            downloadDocument(it)
        }
    }

    suspend fun downloadDocument(order: IncomeDataOrders.Order) {

        var assemblyOrder: AssemblyOrder = downloadAssemblyOrder(order)
        clearTableGoods(assemblyOrder)
        downloadrTableGoods(assemblyOrder, order.tableGoods)
        deleteInappropriateStamps(assemblyOrder)

    }

    private fun clearTableGoods(assemblyOrder: AssemblyOrder) {
        assemblyOrderTableGoodsDao.deleteByAssemblyOrderId(assemblyOrder.id)
    }

    private suspend fun deleteInappropriateStamps(assemblyOrder: AssemblyOrder) {
        val tableStamps = assemblyOrderTableStampsDao.getTableStampsByDocId(assemblyOrder.id)

        tableStamps.forEach {

            val tableGoods = assemblyOrderTableGoodsDao.getTableGoodsByDocAndProduct(
                it.assemblyOrderId,
                it.productId
            )

            if (tableGoods.isEmpty()) {
                assemblyOrderTableStampsDao.delete(it)
            }
        }
    }

    private suspend fun downloadrTableGoods(
        assemblyOrder: AssemblyOrder,
        tableGoods: List<IncomeDataOrders.Order.TableGood>
    ) {

        tableGoods.forEach { tg ->

            val product = productDao.getProductByGuid(tg.productSourceId)

            var newRowTableGoods = AssemblyOrderTableGoods(
                0,
                tg.sourceGuid,
                tg.rowNumber,
                tg.qty,
                0.0,
                product!!.hasStamp!!,
                assemblyOrder.id,
                product.id
            )

            //ПОСЧИТАЕМ УЖЕ ПОДОБРАННЫЕ МАРКИ
            val tableStamps = RawQuery.getTableStampsByGuidAndProduct(
                assemblyOrder.guid,
                product.id
            )
            newRowTableGoods.qtyCollected = tableStamps.size.toDouble()
            newRowTableGoods.id = assemblyOrderTableGoodsDao.insert(newRowTableGoods)

            //Обновим ID ранее подобранных марок
            tableStamps.forEach { item ->
                item!!.assemblyOrderId = assemblyOrder.id
                assemblyOrderTableStampsDao.run { update(item) }
            }
        }
    }

    private suspend fun downloadAssemblyOrder(i: IncomeDataOrders.Order): AssemblyOrder {

        var assemblyOrder = assemblyOrderDao.getAssemblyOrderByGuid(i.guid)

        if (assemblyOrder == null) {
            assemblyOrder = AssemblyOrder()
        }

        assemblyOrder.guid = i.guid
        assemblyOrder.date = i.date
        assemblyOrder.number = i.number
        assemblyOrder.counterpart = i.contractor
        assemblyOrder.comment = i.comment
        assemblyOrder.isCompleted = false
        assemblyOrder.isSent = false

        assemblyOrder.id = assemblyOrderDao.insert(assemblyOrder)

        return assemblyOrder
    }

}