package ru.kbs41.kbsdatacollector

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository

class MainViewModel(private val repository: AssemblyOrderRepository) : ViewModel() {


    val test = MutableLiveData<String>()
    val allOrders: LiveData<List<AssemblyOrder>> = repository.allSortedAssemblyOrders.asLiveData()

    fun insert(assemblyOrder: AssemblyOrder) = viewModelScope.launch {
        repository.insert(assemblyOrder)
    }

    init {
        test.value = "Sexy"
    }

    /*
    val allOrders: LiveData<List<AssemblyOrder>> = repository.allSortedAssemblyOrders.asLiveData()

    fun insert(assemblyOrder: AssemblyOrder) = viewModelScope.launch {
        repository.insert(assemblyOrder)
    }

     */


}

class MainViewModelFactory(private val repository: AssemblyOrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}