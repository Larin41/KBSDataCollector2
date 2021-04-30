package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.ProductDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAlphabetizedProducts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
        Log.d("INSERTING", "SAVED")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getProductByGuid(guid: String) {
        productDao.getProductByGuid(guid)
    }


}