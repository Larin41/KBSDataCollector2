package ru.kbs41.kbsdatacollector.ui.stamps

import androidx.lifecycle.*
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class StampsViewModel() : ViewModel() {

    val repository = AssemblyOrderFullRepository()

    lateinit var product: LiveData<List<Product>>

    lateinit var tableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableStamps: LiveData<List<AssemblyOrderTableStamps>>

    private var docId: Long = 0
    private var productId: Long = 0

    fun initProperties(_docId: Long, _productId: Long) {

        docId = _docId
        productId = _productId

        product = repository.getProduct(productId).asLiveData()
        tableStampsWithProducts = repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(docId, productId).asLiveData()
        tableStamps = repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductId(docId, productId).asLiveData()

    }

    suspend fun insertNewStamp(barcode: String) {

        val item = product.value?.get(0)?.let {
            AssemblyOrderTableStamps(
                0,
                barcode,
                docId,
                productId
            )
        }

        if (item != null) {
            repository.insertAssemblyOrderTableStamps(item)
        }

    }

}
