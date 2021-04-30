package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import androidx.annotation.WorkerThread
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.StampDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.Stamp

import kotlinx.coroutines.flow.Flow

class StampRepository(private val stampDao: StampDao) {

    val allProducts: Flow<List<Stamp>> = stampDao.getSortedStamps()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(stamp: Stamp) {
        stampDao.insert(stamp)
    }
}