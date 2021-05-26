package ru.kbs41.kbsdatacollector.dataSources.network.senders

import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import java.util.*

object SimpleScanningSender {

    val database = App().database

    private val productDao = database.productDao()
    private val simpleScanningDao = database.simpleScanningDao()
    private val simpleScanningTableGoodsDao = database.simpleScanningTableGoodsDao()

    suspend fun makeOrderIsSent(simpleScanning: SimpleScanning){
        simpleScanning.isSent = true
        simpleScanning.comment = simpleScanning.comment + "\n" + FormatManager.getDateRussianFormat(Date())
        simpleScanningDao.insert(simpleScanning)
    }

    suspend fun getAllAssemblyOrdersForSending(): List<SimpleScanning> {

        return simpleScanningDao.getCompletedNotSended()
    }

    suspend fun getTableGoods(simpleScanning: SimpleScanning): List<DataOutgoing.OrderModel.TableGoodsModel> {

        val tableGoods = simpleScanningTableGoodsDao.getByDocId(simpleScanning.id)

        var tableGoodsOutModel: MutableList<DataOutgoing.OrderModel.TableGoodsModel> = arrayListOf()

        tableGoods.forEach {

            val product = productDao.getProductById(it.productId)
            val newItemTableGoods = DataOutgoing.OrderModel.TableGoodsModel(
                product.name,
                product.guid.toString(),
                it.qty,
                it.qty
            )
            tableGoodsOutModel.add(newItemTableGoods)
        }

        return tableGoodsOutModel.toList()
    }

}