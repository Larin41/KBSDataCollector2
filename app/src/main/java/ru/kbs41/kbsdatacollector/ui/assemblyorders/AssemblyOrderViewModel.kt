package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.soundManager.SoundEffects
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.dataSources.dataBase.repository.AssemblyOrderFullRepository

class AssemblyOrderViewModel() : ViewModel() {

    var docId: Long = 0
    var context: Context? = null

    val repository = AssemblyOrderFullRepository()

    lateinit var currentAssemblyOrder: AssemblyOrder

    lateinit var tableGoods: MutableLiveData<List<AssemblyOrderTableGoods>>

    lateinit var tableStamps: MutableLiveData<List<AssemblyOrderTableStamps>>

    lateinit var assemblyOrderTableGoodsWithProducts: MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>

    lateinit var assemblyOrderTableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableQtyQtyCollected: MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

    var editGoods: MutableLiveData<Boolean> = MutableLiveData(false)

    val database = App().database
    val productDao = database.productDao()
    val barcodeDao = database.barcodeDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()

    fun fetchData(_context: Context, _docId: Long) {

        docId = _docId
        context = _context


        //NOT LIVE DATA
        currentAssemblyOrder = repository.getAssemblyOrder(docId)


        //LIVE DATA
        tableQtyQtyCollected = repository.getTableGoodsWithStamps(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

        tableGoods = repository.getAssemblyOrderTableGoodsFlow(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoods>>
        tableStamps = repository.getAssemblyOrderTableStampsByDocIdFlow(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableStamps>>
        assemblyOrderTableGoodsWithProducts =
            repository.getAssemblyOrderTableGoodsWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>
        assemblyOrderTableStampsWithProducts =
            repository.getAssemblyOrderTableStampsWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>
        tableStampsWithProducts =
            repository.getAssemblyOrderTableStampsByAssemblyOrderIdWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>


    }

    fun setQtyCollectedTableGoods(tableGoodsId: Long, setZero: Boolean = false) {
        var tableGoodsRow = repository.getAssemblyOrderTableGoodsByRowId(tableGoodsId)
        if (setZero) {
            tableGoodsRow.qtyCollected = 0.0
        } else {
            tableGoodsRow.qtyCollected = tableGoodsRow.qty
        }

        repository.updateTableGoods(tableGoodsRow)
    }

    fun completeOrder(): Boolean {

        val list = repository.getAssemblyOrderTableGoods(docId)
        var isCollected = true

        list.forEach {
            if (it.qty != it.qtyCollected) {
                isCollected = false
            }
        }

        if (isCollected) {
            currentAssemblyOrder.isCompleted = true
            repository.updateAssemblyOrder(currentAssemblyOrder)
            return true
        } else {
            Toast.makeText(
                context!!,
                context!!.getString(R.string.products_not_collected),
                Toast.LENGTH_SHORT
            ).show()
            GlobalScope.launch(Dispatchers.Main) { SoundEffects().playError(context!!) }
            return false
        }

    }

    fun barcodeReading(barcode: String) {

        GlobalScope.launch(Dispatchers.Main) {
            if (barcode.length > 13) {
                Toast.makeText(
                    context,
                    "Формат штрихкода не соответвует продукции",
                    Toast.LENGTH_SHORT
                ).show()
                SoundEffects().playError(context!!)
            } else {
                createNewNote(barcode)
            }
        }

    }

    fun createNewNote(barcode: String) {

        GlobalScope.launch(Dispatchers.IO) {

            val barcodeNote = barcodeDao.getOneNoteByBarcode(barcode)
            if (barcodeNote == null) {
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        context,
                        "Нет известных товаров соответсвующих данному штрихкоду",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                SoundEffects().playError(context!!)
            } else {
                val product = productDao.getProductById(barcodeNote.productId)

                val newRowTableGoods = AssemblyOrderTableGoods()
                newRowTableGoods.addedManually = true
                newRowTableGoods.assemblyOrderId = currentAssemblyOrder.id
                newRowTableGoods.productId = product.id
                newRowTableGoods.needStamp = product.hasStamp!!
                newRowTableGoods.row =
                    assemblyOrderTableGoodsDao.getLatestRow(currentAssemblyOrder.id)

                assemblyOrderTableGoodsDao.insert(newRowTableGoods)
            }

        }
    }

}

