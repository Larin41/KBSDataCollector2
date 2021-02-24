package ru.kbs41.kbsdatacollector.retrofit


import android.app.Application
import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.dao.ProductDao
import ru.kbs41.kbsdatacollector.room.db.*

class ExchangeMaster {

    fun getBaseURL(): String {

        return "http://192.168.1.50"
    }


    fun getData(application: Application) {

        val baseUrl = getBaseURL()

        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getData().awaitResponse()
            if (response.isSuccessful) {
                val data: DataJSON = response.body()!!

                //DATABASE
                val database = (application as App).database

                //GOODS / PRODUCTS
                for (i in data.goods) {
                    insertProduct(i, database)
                }

                //ORDERS
                for (i in data.orders) {
                    Log.d("ORDERS", i.number)
                    insertAssemblyOrder(i, database)
                }
            }
        }
    }

    suspend fun insertAssemblyOrder(i: DataJSON.Order, database: AppDatabase) {

        val assemblyOrderDao = database.assemblyOrderDao()
        val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
        val productDao = database.productDao()

        val assablyOrders = assemblyOrderDao.getAssemblyOrderByGuid(i.guid)

        var orderId: Long = 0
        var isComplated = false
        var isSent = false
        if (assablyOrders.size > 0) {
            orderId = assablyOrders[0].id
            isComplated = assablyOrders[0].isCompleted
            isSent = assablyOrders[0].isSent
        }

        val assemblyOrder = AssemblyOrder(
            orderId,
            i.guid,
            i.date,
            i.number,
            i.contractor,
            i.comment,
            isComplated,
            isSent
        )

        if (assablyOrders.size > 0) {
            assemblyOrderDao.update(assemblyOrder)
        } else {
            orderId = assemblyOrderDao.insert(assemblyOrder)
        }

        //СНАЧАЛА УДАЛЯЕМ ВСЁ НАХ
        assemblyOrderTableGoodsDao.deleteTableBySourceGuid(i.guid)

        for (t in i.tableGoods) {

            val assemblyOrderTableGoods = AssemblyOrderTableGoods(
                0,
                t.sourceGuid,
                t.rowNumber,
                t.qty,
                0.toDouble(),
                orderId,
                productDao.getProductByGuid(t.productSourceId)[0].id
            )

            assemblyOrderTableGoodsDao.insert(assemblyOrderTableGoods)
        }

        //TODO: удалить лишние товары из таблицы марок STAMPS

    }

    suspend fun insertProduct(i: DataJSON.Good, database: AppDatabase) {

        val productDao = database.productDao()
        val barcodeDao = database.barcodeDao()
        val stampsDao = database.stampDao()

        //PRODUCTS
        val products = productDao.getProductByGuid(i.guid)
        var productId: Long = 0
        if (products.size > 0) {
            productId = products[0].id
        }

        val parents = productDao.getProductByGuid(i.parentGuid)

        var parentId: Long = 0
        if (parents.size > 0) {
            parentId = parents[0].id
        }

        val product: Product = Product(
            productId,
            i.name,
            i.unit,
            i.isAlcohol,
            i.hasStamp,
            i.guid,
            i.isFolder,
            i.parentGuid,
            parentId
        )

        if (products.size > 0) {
            productDao.update(product)
            Log.d("UPDATE", i.name)
        } else {
            productId = productDao.insert(product)
            Log.d("INSERT", i.name)
        }

        //BARCODES
        if (i.barcodes != null) {
            for (b in i.barcodes) {

                var barcodes = barcodeDao.getNoteByBarcode(b.barcode)
                var barcodeId: Long = 0
                if (barcodes.size > 0) {
                    barcodeId = barcodes[0].id
                }

                val barcode: Barcode = Barcode(
                    barcodeId,
                    b.barcode,
                    productId
                )

                barcodeDao.insert(barcode)
            }
        }

        //STAMPS
        if (i.stamps != null) {
            for (s in i.stamps) {

                var stamps = stampsDao.getNoteByStamp(s.stamp)
                var stampId: Long = 0
                if (stamps.size > 0) {
                    stampId = stamps[0].id
                }

                val stamp: Stamp = Stamp(
                    stampId,
                    s.stamp,
                    productId
                )

                stampsDao.insert(stamp)
            }
        }

    }
}