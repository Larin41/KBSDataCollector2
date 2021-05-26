package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument

import android.content.Context
import android.os.Debug
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
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
    val comment: MutableLiveData<String> = MutableLiveData("")

    var currentProductId: Long = 0L
    var latestPosition: Int = 0

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
                simpleScanningTableGoodsDao.getByDocIdLiveData(id.value!!)
        }

    }

    fun barcodeReading(barcode: String) {

        val barcodeNote = barcodeDao.getOneNoteByBarcode(barcode)

        if (barcodeNote == null) {
            GlobalScope.launch { SoundEffects().playError(context) }
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Незвестный штрихкод",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
        simpleScanningTableGoodsDao.insert(rowTableGoods)
        currentProductId = rowTableGoods.productId

    }

    private fun initExisted(_docId: Long) {
        simpleScanning = simpleScanningDao.getById(_docId)!!
        date.value = simpleScanning.date
        id.apply { id.value = simpleScanning.id }
        comment.apply { comment.value = simpleScanning.comment }

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

        simpleScanning.id = simpleScanningDao.insert(simpleScanning)
        id.let { id.value = simpleScanning.id }

    }

    fun updateComment(comment: String) {
        simpleScanning.comment = comment
        simpleScanningDao.insert(simpleScanning)
    }

    fun sendSimpleScanning(){
        simpleScanning.isCompleted = true
        simpleScanning.isSent = false
        simpleScanningDao.insert(simpleScanning)
    }

    fun getLastestPosition(): Int {

        var index: Int = 0

        tableGoods.value?.forEach {
            if (it.productId == currentProductId){
                index = tableGoods.value!!.indexOf(it)
                return index
            }
        }

        return 0
    }
}

