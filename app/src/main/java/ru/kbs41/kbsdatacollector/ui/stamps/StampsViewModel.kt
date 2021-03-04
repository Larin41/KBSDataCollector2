package ru.kbs41.kbsdatacollector.ui.stamps

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModel

class StampsViewModel(docId: Long, productId: Long) : ViewModel() {

    val repository = AssemblyOrderFullRepository()

    val product: LiveData<List<Product>> = repository.getProduct(productId).asLiveData()

    val tableStamps: LiveData<List<AssemblyOrderTableStamps>> =
        repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductId(docId, productId)
            .asLiveData()

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