package com.android.example.kbsdatacollector.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.db.Barcode


@Dao
interface BarcodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcode: Barcode)

    @Delete
    suspend fun delete(barcode: Barcode)
}