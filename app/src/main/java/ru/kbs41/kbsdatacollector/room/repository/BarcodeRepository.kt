package ru.kbs41.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.dao.BarcodeDao
import ru.kbs41.kbsdatacollector.room.db.Barcode

class BarcodeRepository(private val barcodeDao: BarcodeDao) {

    val allProducts: Flow<List<Barcode>> = barcodeDao.getSortedBarcodes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(barcode: Barcode) {
        barcodeDao.insert(barcode)
    }
}