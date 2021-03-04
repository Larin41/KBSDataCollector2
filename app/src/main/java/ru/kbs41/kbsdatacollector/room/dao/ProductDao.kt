package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAlphabetizedProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE guid = :productGuid")
    fun getProductByGuid(productGuid: String): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Long): Flow<List<Product>>

}