package ru.kbs41.kbsdatacollector.ui.stamps

import androidx.lifecycle.*
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class StampsViewModel(_docId: Long, _productId: Long) : ViewModel() {


    val docId = _docId
    val productId = _productId

    val repository = AssemblyOrderFullRepository()

    val product: LiveData<List<Product>> = repository.getProduct(productId).asLiveData()

    val tableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>> =
        repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(docId, productId).asLiveData()

    val tableStamps: LiveData<List<AssemblyOrderTableStamps>> =
        repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductId(docId, productId)
            .asLiveData()


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

class StampsViewModelFactory(private val docId: Long, private val productId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StampsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StampsViewModel(docId, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}