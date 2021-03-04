package ru.kbs41.kbsdatacollector.ui

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository

class AssemblyOrderViewModel(id: Long) : ViewModel() {


    val repository = AssemblyOrderFullRepository()

    val assemblyOrders: LiveData<List<AssemblyOrder>> = repository.getAssemblyOrder(id).asLiveData()

    val tableGoods: LiveData<List<AssemblyOrderTableGoods>> =
        repository.getAssemblyOrderTableGoods(id).asLiveData()

    val tableStamps: LiveData<List<AssemblyOrderTableStamps>> =
        repository.getAssemblyOrderTableStamps(id).asLiveData()

    val assemblyOrderTableGoodsWithProducts: LiveData<List<AssemblyOrderTableGoodsWithProducts>> =
        repository.getAssemblyOrderTableGoodsWithProducts(id).asLiveData()

    val assemblyOrderTableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>> =
        repository.getAssemblyOrderTableStampsWithProducts(id).asLiveData()

    //TODO: Это экспереминтальная функция, удалить в продакшане, если не задейстованно
    val assemblyOrderWithTables: LiveData<List<AssemblyOrderWithTables>> =
        repository.getAssemblyOrderWithTables(id).asLiveData()

}

class AssemblyOrderViewModelFactory(private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssemblyOrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssemblyOrderViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
