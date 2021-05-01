package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument

import android.os.Debug
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningTableGoods
import java.util.*

class SimpleScanningViewModel() : ViewModel() {

    var isNew: Boolean = true

    val database = App().database
    val simpleScanningDao = database.simpleScanningDao()
    val simpleScanningTableGoodsDao = database.simpleScanningTableGoodsDao()

    val id: MutableLiveData<Long> = MutableLiveData(0L)
    val date: MutableLiveData<Date> = MutableLiveData(Date())

    lateinit var tableGoods: LiveData<List<SimpleScanningTableGoods>>

    lateinit var doc: SimpleScanning
    //lateinit var tableGoods: SimpleScanningTableGoods

    lateinit var simpleScanning: SimpleScanning


    fun fetchData(_docId: Long) {

        if (_docId == 0L) {
            initNew()
        } else {
            initExisted(_docId)
        }

        if (id.value?.toLong() != null) {
            tableGoods =
                simpleScanningTableGoodsDao.getByDocId(id.value!!)
        }


    }

    private fun initExisted(_docId: Long) {
        doc = simpleScanningDao.getById(_docId)!!
        date.value = doc.date
        id.apply { id.value = doc.id }

    }

    private fun initNew() {

        val latestDoc = simpleScanningDao.getDocWithLastestId()
        if (latestDoc != null) {
            id.apply { id.value = latestDoc.id + 1 }
        } else {
            //Debug.waitForDebugger()
            id.apply { id.value = 1 }
        }


    }

}

