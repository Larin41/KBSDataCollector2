package ru.kbs41.kbsdatacollector.dataSources.dataBase.products

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("SELECT * FROM products")
    fun getAllFlow(): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAlphabetizedProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE guid = :productGuid LIMIT 1")
    fun getProductByGuid(productGuid: String): Product?

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    fun getProductByIdFlow(id: Long): Flow<Product>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    fun getProductById(id: Long): Product

}