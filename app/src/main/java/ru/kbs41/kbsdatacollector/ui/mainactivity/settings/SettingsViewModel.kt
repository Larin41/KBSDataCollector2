package ru.kbs41.kbsdatacollector.ui.mainactivity.settings



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.db.Settings


class SettingsViewModel : ViewModel() {

    private val settingsDao = App().database.settingsDao()

    val currentSetting: MutableLiveData<Settings> = settingsDao.getCurrentSettings().asLiveData() as MutableLiveData<Settings>
    val allSettings: LiveData<List<Settings>> = settingsDao.getAllSettings().asLiveData()




}