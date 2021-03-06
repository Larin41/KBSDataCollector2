package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.SoundEffects
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class StampsViewModel() : ViewModel() {

    private lateinit var context: Context

    private var qty: Double = 0.0
    private var docId: Long = 0
    private var productId: Long = 0

    private val repository = AssemblyOrderFullRepository()

    lateinit var product: LiveData<Product>
    lateinit var tableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>>

    fun getQty():Double { return qty }

    fun initProperties(_docId: Long, _productId: Long, _qty: Double, _context: Context) {

        context = _context
        qty = _qty
        docId = _docId
        productId = _productId

        product = repository.getProduct(productId).asLiveData()
        tableStampsWithProducts = repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(docId, productId).asLiveData()

    }

    suspend fun insertNewStamp(barcode: String) {

        if (tableStampsWithProducts.value != null){
            if (qty.compareTo(tableStampsWithProducts.value!!.size) <= 0){
                GlobalScope.launch(Dispatchers.IO) { SoundEffects().playError(context) }
                return
            }
        }

        val item = product.value.let {
            AssemblyOrderTableStamps(
                0,
                barcode,
                docId,
                productId
            )
        }

        repository.insertAssemblyOrderTableStamps(item)
    }
}
