package ru.kbs41.kbsdatacollector.dataSources.dataBase.repository

import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings

class SettingsRepository {

    val database: AppDatabase = App().database
    val settingsDao = database.settingsDao()

    fun getAllSettings(): Flow<List<Settings>> {
        return settingsDao.getAllSettings()
    }

}