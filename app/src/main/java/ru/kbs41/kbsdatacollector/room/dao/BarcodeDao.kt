package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.Barcode


@Dao
interface BarcodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcode: Barcode)

    @Delete
    suspend fun delete(barcode: Barcode)

    @Query("SELECT * FROM barcodes ORDER BY barcode ASC")
    fun getSortedBarcodes(): Flow<List<Barcode>>

    @Query("SELECT * FROM barcodes WHERE barcode = :barcode")
    fun getNoteByBarcode(barcode: String): List<Barcode>
}