package ru.kbs41.kbsdatacollector.ui.mainactivity.settings


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.databinding.SettingsFragmentBinding
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings


class SettingsViewModel : ViewModel() {

    private val settingsDao = App().database.settingsDao()
    private lateinit var binding: SettingsFragmentBinding

    var currentSetting: Settings = settingsDao.getCurrentSettings()


    fun fetchData(_binding: SettingsFragmentBinding) {

        binding = _binding

        updateBinding()

    }


    fun updateBinding() {

        currentSetting = settingsDao.getCurrentSettings()

        if (currentSetting == null){
            currentSetting = Settings()
        }

        binding.useHttps = currentSetting.useHttps
        binding.representation = currentSetting.representation
        binding.server = currentSetting.server
        binding.port = currentSetting.port
        binding.user = currentSetting.user
        binding.password = currentSetting.password

    }

    fun updateSettings() {

        if (currentSetting == null){
            currentSetting = Settings()
        }

        currentSetting.useHttps = binding.useHttps!!
        currentSetting.representation = binding.representation!!
        currentSetting.server = binding.server!!
        currentSetting.port = binding.port!!
        currentSetting.user = binding.user!!
        currentSetting.password = binding.password!!
        currentSetting.isCurrent = true


        GlobalScope.launch(Dispatchers.IO) {
            settingsDao.insert(currentSetting)
            updateBinding()
        }


    }


}