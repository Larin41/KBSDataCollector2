package ru.kbs41.kbsdatacollector.dataSources.network.downloaders

import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes.Barcode
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.ProductDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.Stamp
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataIncome

class GoodsDownloader {

    val database = App().database
    val productDao = database.productDao()
    val barcodeDao = database.barcodeDao()
    val stampsDao = database.stampDao()


    suspend fun downloadCatalogs(goodsList: List<DataIncome.Good>?) {
        goodsList?.forEach {
            downloadCatalog(it)
        }
    }

    suspend fun downloadCatalog(i: DataIncome.Good) {

        val product = downloadProduct(i)
        downloadBarcodes(product, i.barcodes)
        downloadStamps(product, i.stamps)

    }

    private suspend fun downloadStamps(
        product: Product,
        stamps: List<DataIncome.Good.Stamp>?
    ) {
        stamps?.forEach { bc ->
            val stampNote = stampsDao.getOneNoteByStamp(bc.stamp)

            val stamp: Stamp = Stamp(
                stampNote.id,
                bc.stamp,
                product.id
            )
            stampsDao.insert(stamp)
        }
    }

    private suspend fun downloadBarcodes(
        product: Product,
        barcodes: List<DataIncome.Good.Barcode>?
    ) {

        barcodes?.forEach { bc ->
            val barcodeNote = barcodeDao.getOneNoteByBarcode(bc.barcode)

            val barcode: Barcode = Barcode(
                barcodeNote.id,
                bc.barcode,
                product.id
            )

            barcodeDao.insert(barcode)
        }

    }

    suspend private fun downloadProduct(i: DataIncome.Good): Product {
        //ПРОБУЕМ НАЙТИ ТОВАР ПО ГУИД
        var product = productDao.getProductByGuid(i.guid)
        if (product == null) {
            product = Product()
        }

        //ПОЛУЧИВШИЕЕСЯ ДАННЫЕ ЗАПИШЕМ
        product.name = i.name
        product.unit = i.unit
        product.isAlcohol = i.isAlcohol
        product.hasStamp = i.hasStamp
        product.guid = i.guid
        product.isFolder = i.isFolder
        product.parentGuid = i.parentGuid
        product.parentId = getParentProduct(i.parentGuid, productDao)

        product.id = productDao.insert(product)

        return product
    }

    private fun getParentProduct(guid: String, productDao: ProductDao): Long {
        val product = productDao.getProductByGuid(guid) ?: return 0
        return product.id
    }

}


