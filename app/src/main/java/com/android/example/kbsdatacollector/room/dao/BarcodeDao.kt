package com.android.example.kbsdatacollector.room.dao

import androidx.room.*
import com.android.example.kbsdatacollector.room.db.Barcode
import kotlinx.coroutines.flow.Flow


@Dao
interface BarcodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcode: Barcode)

    @Delete
    suspend fun delete(barcode: Barcode)

    @Query("SELECT * FROM barcodes ORDER BY barcode ASC")
    fun getSortedBarcodes(): Flow<List<Barcode>>
}