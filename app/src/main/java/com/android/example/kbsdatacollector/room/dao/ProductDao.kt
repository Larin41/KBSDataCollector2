package com.android.example.kbsdatacollector.room.dao

import androidx.room.*
import com.android.example.kbsdatacollector.room.db.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAlphabetizedProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE guid = :productGuid")
    fun getProductByGuid(productGuid: String): List<Product>

}