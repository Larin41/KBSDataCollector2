package com.android.example.kbsdatacollector.dao

import androidx.room.*
import com.android.example.kbsdatacollector.db.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

}