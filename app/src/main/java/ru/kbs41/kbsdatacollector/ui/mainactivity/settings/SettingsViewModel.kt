package ru.kbs41.kbsdatacollector.ui.mainactivity.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.databinding.SettingsFragmentBinding
import ru.kbs41.kbsdatacollector.room.db.Settings


class SettingsViewModel : ViewModel() {

    private val settingsDao = App().database.settingsDao()
    private var binding: SettingsFragmentBinding? = null

    var currentSetting: Settings = settingsDao.getCurrentSettings()


    fun fetchData(_binding: SettingsFragmentBinding) {

        binding = _binding

        updateBinding()

    }


    fun updateBinding() {

        currentSetting = settingsDao.getCurrentSettings()

        binding!!.useHttps = currentSetting.useHttps
        binding!!.representation = currentSetting.representation
        binding!!.server = currentSetting.server
        binding!!.port = currentSetting.port
        binding!!.user = currentSetting.user
        binding!!.password = currentSetting.password

    }

    fun updateSettings() {

        currentSetting.useHttps = binding!!.useHttps
        currentSetting.representation = binding!!.representation
        currentSetting.server = binding!!.server
        currentSetting.port = binding!!.port
        currentSetting.user = binding!!.user
        currentSetting.password = binding!!.password
        currentSetting.isCurrent = true


        GlobalScope.launch(Dispatchers.IO) {
            settingsDao.insert(currentSetting)
            updateBinding()
        }


    }


}