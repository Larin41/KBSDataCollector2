package ru.kbs41.kbsdatacollector.ui

import androidx.lifecycle.*
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository

class AssemblyOrderViewModel(id: Long) : ViewModel() {

    val repository = AssemblyOrderFullRepository()
    val assemblyOrders: LiveData<List<AssemblyOrder>> = repository.getAssemblyOrder(id).asLiveData()
    val tableGoods: LiveData<List<AssemblyOrderTableGoods>> = repository.getAssemblyOrderTableGoods(id).asLiveData()
    val tableStamps: LiveData<List<AssemblyOrderTableStamps>> = repository.getAssemblyOrderTableStamps(id).asLiveData()

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
