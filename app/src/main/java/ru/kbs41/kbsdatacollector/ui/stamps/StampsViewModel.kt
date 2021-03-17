package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kbs41.kbsdatacollector.SoundEffects
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class StampsViewModel() : ViewModel() {

    private lateinit var context: Context

    private var currentRowTableGoodsId: Long = 0
    var qty: MutableLiveData<Double> = MutableLiveData(0.0)
    var qtyCollected: MutableLiveData<Double> = MutableLiveData(0.0)
    private var docId: Long = 0
    private var productId: Long = 0

    private val repository = AssemblyOrderFullRepository()

    //NOT LIVE DATA
    lateinit var currentProduct: Product
    lateinit var currentAssemblyOrder: AssemblyOrder
    lateinit var currentRowTableGoods: AssemblyOrderTableGoods

    //LIVE DATA
    lateinit var tableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>>
    lateinit var tableGoods: LiveData<List<AssemblyOrderTableGoods>>
    lateinit var tableStamps: LiveData<List<AssemblyOrderTableStamps>>

    fun initProperties(
        _currentRowTableGoodsId: Long,
        _docId: Long,
        _productId: Long,
        _qty: Double,
        _context: Context
    ) {

        context = _context
        currentRowTableGoodsId = _currentRowTableGoodsId
        qty.value = _qty
        docId = _docId
        productId = _productId

        //NOT A LIVE DATA
        currentProduct = repository.getProduct(productId)
        currentAssemblyOrder = repository.getAssemblyOrder(docId)
        currentRowTableGoods = repository.getOneRowTableGoodsWithProductById(currentRowTableGoodsId)


        //LIVE DATA
        tableGoods = repository.getAssemblyOrderTableGoodsFlow(docId).asLiveData()
        tableStamps =
            repository.getAssemblyOrderTableStampsByDocIdAndProductIdFlow(docId, productId)
                .asLiveData()
        tableStampsWithProducts =
            repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(
                docId,
                productId
            ).asLiveData()

        tableStamps.observeForever {
            if (it != null) {

                val size = it.size.toDouble()

                //UPDATE TABLE GOODS
                currentRowTableGoods.qtyCollected = size
                repository.updateTableGoods(currentRowTableGoods)

                qtyCollected.value = size
            }
        }
    }

    suspend fun insertNewStamp(barcode: String) {


        //ПРОВЕРИМ МАРКУ НА ДУБЛЬ, ЧТОБЫ НЕДОПУСТИТЬ СЧИТЫВАНИЯ ОДНОЙ МАРКИ НЕСКОЛЬКО РАЗ
        val existedEntry = repository.getAssemblyOrderTableStampsByBarcode(barcode)

        if (existedEntry != null) {
            GlobalScope.launch(Dispatchers.Main) {
                SoundEffects().playError(context)
                Toast.makeText(context, "Данная марка уже была считана!", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val cTableStamps: List<AssemblyOrderTableStamps> =
            repository.getAssemblyOrderTableStampsByDocIdAndProductId(docId, productId)

        val pQtyCollected = cTableStamps.size.toDouble()

        if (qty.value == pQtyCollected) {
            GlobalScope.launch(Dispatchers.Main) { SoundEffects().playError(context) }
            return
        }

        if (currentRowTableGoods.qty == pQtyCollected + 1) {
            GlobalScope.launch(Dispatchers.Main) { SoundEffects().playSuccess(context) }
        }

        val newItem = AssemblyOrderTableStamps(
            0,
            barcode,
            currentAssemblyOrder.id,
            currentProduct.id
        )


        //INSERT NEW BARCODE
        repository.insertAssemblyOrderTableStamps(newItem)

    }
}
