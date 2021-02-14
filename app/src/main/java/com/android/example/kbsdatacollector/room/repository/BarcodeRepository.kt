package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.BarcodeDao
import com.android.example.kbsdatacollector.room.db.Barcode
import kotlinx.coroutines.flow.Flow

class BarcodeRepository(private val barcodeDao: BarcodeDao) {

    val allProducts: Flow<List<Barcode>> = barcodeDao.getSortedBarcodes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(barcode: Barcode) {
        barcodeDao.insert(barcode)
    }
}