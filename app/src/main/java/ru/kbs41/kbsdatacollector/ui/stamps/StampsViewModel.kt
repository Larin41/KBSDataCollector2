package ru.kbs41.kbsdatacollector.ui.stamps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.BarcodeDatamatrix
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product

class StampsViewModel() : ViewModel() {

    //private val applicationContext = App().applicationContext

    private val database = App().database
    private val productDao = database.productDao()
    private val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    private val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()

    private var currentProductId: Long = 0
    private var currentOrderId: Long = 0
    private var currentRowId: Long = 0

    lateinit var product: Product
    lateinit var rowTableGoods: LiveData<AssemblyOrderTableGoods>
    lateinit var tableStamps: LiveData<List<AssemblyOrderTableStamps>>

    var addedManually: Boolean = false


    fun fetchData(
        _currentProductId: Long,
        _currentRowId: Long,
        _currentOrderId: Long,
        _addedManually: Boolean
    ) {

        addedManually = _addedManually
        currentProductId = _currentProductId
        currentOrderId = _currentOrderId
        currentRowId = _currentRowId

        product = productDao.getProductById(currentProductId)
        rowTableGoods = assemblyOrderTableGoodsDao.getRowById(currentRowId).asLiveData()
        tableStamps =
            assemblyOrderTableStampsDao.getTableStampsByTableGoodsRow(currentRowId).asLiveData()

    }

    private fun parseStamp(barcode: String): String {
        val barcodeDatamatrix = BarcodeDatamatrix()
        barcodeDatamatrix.parseDataMatrixBarcode(barcode)
        return barcodeDatamatrix.finalData
    }

    suspend fun insertNewStamp(barcode: String): ErrorsDescription {

        val errorDescription = checkBarcodeForProblems(barcode)
        if (errorDescription.hasProblems) {
            return errorDescription
        }


        val parsedBarcode = parseStamp(barcode)

        val newNote = AssemblyOrderTableStamps(
            0,
            currentRowId,
            parsedBarcode,
            currentOrderId,
            currentProductId
        )

        assemblyOrderTableStampsDao.insert(newNote)

        return errorDescription

    }

    fun updateRowTableGoods(qtyCollected: Double) {

        rowTableGoods.value?.let {
            it.qtyCollected = qtyCollected
            if (addedManually) {
                it.qty = qtyCollected
            }

            GlobalScope.launch(Dispatchers.IO) { assemblyOrderTableGoodsDao.update(it) }

        }
    }

    private suspend fun checkBarcodeForProblems(barcode: String): ErrorsDescription {

        //Debug.waitForDebugger()

        val stampsAreCollected: Boolean = stampsAreAlreadyCollected()
        val problemsWithBarcodeFormat: Boolean = checkForLength(barcode)

        val parsedBarcode = parseStamp(barcode)
        val problemsWithExistedStamp: Boolean = checkForExisted(parsedBarcode, currentOrderId)

        val scanningComplete: Boolean = checkForScanningComplete()


        val errorDescriptions = ErrorsDescription(
            stampsAreCollected || problemsWithBarcodeFormat || problemsWithExistedStamp,
            stampsAreCollected,
            problemsWithBarcodeFormat,
            problemsWithExistedStamp,
            scanningComplete
        )

        return errorDescriptions

    }

    private fun checkForScanningComplete(): Boolean {

        //Debug.waitForDebugger()

        if (rowTableGoods.value?.addedManually == true) {
            return false
        }

        if (tableStamps.value != null && rowTableGoods.value != null) {
            val qtyCollected = tableStamps.value!!.size.toDouble()
            val qty = rowTableGoods.value!!.qty

            return qty == (qtyCollected + 1)

        } else {
            return false
        }

    }

    private fun stampsAreAlreadyCollected(): Boolean {

        if (rowTableGoods.value?.addedManually == true) {
            return false
        }

        if (tableStamps.value != null && rowTableGoods.value != null) {
            val qtyCollected = tableStamps.value?.size?.toDouble()
            val qty = rowTableGoods.value?.qty

            return qty == qtyCollected

        } else {
            return false
        }


    }

    private fun checkForExisted(barcodeParsed: String, currentOrderId: Long): Boolean {

        //Debug.waitForDebugger()

        val existedEntry =
            assemblyOrderTableStampsDao.getTableStampsByBarcodeAndDoc(barcodeParsed, currentOrderId)
        return existedEntry != null
    }

    private fun checkForLength(barcode: String): Boolean {
        return barcode.length < 32
    }
}
