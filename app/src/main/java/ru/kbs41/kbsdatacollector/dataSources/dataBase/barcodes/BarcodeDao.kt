package ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes

import androidx.room.*
import kotlinx.coroutines.flow.Flow


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

    @Query("SELECT * FROM barcodes WHERE barcode = :barcode LIMIT 1")
    fun getOneNoteByBarcode(barcode: String): Barcode

}