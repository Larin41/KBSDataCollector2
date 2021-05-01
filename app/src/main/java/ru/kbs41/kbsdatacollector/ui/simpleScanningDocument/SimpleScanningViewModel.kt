package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument

import android.content.Context
import android.os.Debug
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningTableGoods
import ru.kbs41.kbsdatacollector.soundManager.SoundEffects
import java.util.*

class SimpleScanningViewModel() : ViewModel() {

    private lateinit var context: Context

    var isNew: Boolean = true

    val database = App().database
    val barcodeDao = database.barcodeDao()
    val simpleScanningDao = database.simpleScanningDao()
    val simpleScanningTableGoodsDao = database.simpleScanningTableGoodsDao()

    val id: MutableLiveData<Long> = MutableLiveData(0L)
    val date: MutableLiveData<Date> = MutableLiveData(Date())

    lateinit var tableGoods: LiveData<List<SimpleScanningTableGoods>>

    lateinit var simpleScanning: SimpleScanning



    fun fetchData(_docId: Long, _context: Context) {

        context = _context

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

    fun barcodeReading(barcode: String) {

        val barcodeNote = barcodeDao.getOneNoteByBarcode(barcode)

        if (barcodeNote == null) {
            GlobalScope.launch { SoundEffects().playError(context) }
            Toast.makeText(context, "Незвестный штрихкод", Toast.LENGTH_SHORT).show()
            return
        }

        var rowTableGoods =
            simpleScanningTableGoodsDao.getByDocAndProduct(id.value!!, barcodeNote.productId)

        if (rowTableGoods == null) {
            rowTableGoods = SimpleScanningTableGoods(
                0,
                "",
                1.0,
                id.value!!,
                barcodeNote.productId
            )
        } else {
            rowTableGoods.qty++
        }

        //Debug.waitForDebugger()
        GlobalScope.launch { simpleScanningTableGoodsDao.insert(rowTableGoods) }

    }

    private fun initExisted(_docId: Long) {
        simpleScanning = simpleScanningDao.getById(_docId)!!
        date.value = simpleScanning.date
        id.apply { id.value = simpleScanning.id }

    }

    private fun initNew() {

        simpleScanning = SimpleScanning(
            0,
            "",
            Date(),
            "",
            false,
            false
        )

        id.apply { id.value = simpleScanningDao.insert(simpleScanning) }

    }

    fun updateComment(comment: String) {
        simpleScanning.comment = comment
        simpleScanningDao.insert(simpleScanning)
    }
}

