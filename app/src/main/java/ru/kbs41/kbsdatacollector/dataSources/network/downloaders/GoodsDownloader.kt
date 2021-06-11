package ru.kbs41.kbsdatacollector.dataSources.network.downloaders

import android.os.Debug
import android.util.Log
import android.widget.ProgressBar
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes.Barcode
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.ProductDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.Stamp
import ru.kbs41.kbsdatacollector.dataSources.network.models.Good

object GoodsDownloader {

    val database = App().database
    val productDao = database.productDao()
    val barcodeDao = database.barcodeDao()
    val stampsDao = database.stampDao()


    fun downloadCatalogs(goods: List<Good>, progressBar: ProgressBar? = null) {
        //Debug.waitForDebugger()
        progressBar?.max = goods.size

        var counter = 1
        goods.forEach { product ->
            downloadCatalog(product)
            Log.d("GoodsFrom1C", product.name)
            progressBar?.progress = counter
            counter += 1
        }

        Log.d("GoodsFrom1C", "download completed")
    }

    fun downloadCatalog(i: Good) {

        val product = downloadProduct(i)

        i.barcodes?.let {
            downloadBarcodes(product, i.barcodes)
        }

       /*Функционал временно отключен
       i.stamps?.let {
            downloadStamps(product, i.stamps)
        }*/


    }

    private fun downloadStamps(
        product: Product,
        stamps: List<ru.kbs41.kbsdatacollector.dataSources.network.models.Stamp>?
    ) {
        stamps?.forEach { bc ->
            val stampNote = stampsDao.getOneNoteByStamp(bc.stampValue)

            val stamp: Stamp = Stamp(
                stampNote.id,
                bc.stampValue,
                product.id
            )
            stampsDao.insert(stamp)
        }
    }

    private fun downloadBarcodes(
        product: Product,
        barcodes: List<ru.kbs41.kbsdatacollector.dataSources.network.models.Barcode>?
    ) {

        barcodes?.forEach { bc ->
            val barcodeNote = barcodeDao.getOneNoteByBarcode(bc.barcode)
            var id: Long = 0
            if (barcodeNote != null) {
                id = barcodeNote.id
            }

            val barcode: Barcode = Barcode(
                id,
                bc.barcode,
                product.id
            )

            barcodeDao.insert(barcode)
        }

    }

    private fun downloadProduct(i: Good): Product {
        //ПРОБУЕМ НАЙТИ ТОВАР ПО ГУИД
        var product = productDao.getProductByGuid(i.guid)
        if (product == null) {
            product = Product()
        }

        //ПОЛУЧИВШИЕЕСЯ ДАННЫЕ ЗАПИШЕМ
        product.name = i.name
        product.unit = "шт."
        product.isAlcohol = i.isAlcohol
        product.hasStamp = i.hasStamp
        product.guid = i.guid
        product.isFolder = i.isFolder
        product.parentGuid = i.folderGuid
        product.parentId = getParentProduct(i.folderGuid, productDao)

        product.id = productDao.insert(product)

        return product
    }

    private fun getParentProduct(guid: String, productDao: ProductDao): Long {
        val product = productDao.getProductByGuid(guid) ?: return 0
        return product.id
    }

}


