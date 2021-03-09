package ru.kbs41.kbsdatacollector.room.repository

import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.db.Settings

class SettingsRepository {

    val database: AppDatabase = App().getDatabase()
    val settingsDao = database.settingsDao()

    fun getAllSettings(): Flow<List<Settings>> {
        return settingsDao.getAllSettings()
    }

}