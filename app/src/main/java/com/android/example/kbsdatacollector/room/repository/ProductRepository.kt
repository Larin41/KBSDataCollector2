package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.ProductDao
import com.android.example.kbsdatacollector.room.db.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAlphabetizedProducts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }


}