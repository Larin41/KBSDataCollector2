package ru.kbs41.kbsdatacollector.ui.mainactivity.simpleScanning

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning

class SimpleScanningViewModel : ViewModel() {

    val database = App().database
    private val simpleScanningDao = database.simpleScanningDao()

    val allScannings: LiveData<List<SimpleScanning>> = simpleScanningDao.getAllFlow().asLiveData()


}