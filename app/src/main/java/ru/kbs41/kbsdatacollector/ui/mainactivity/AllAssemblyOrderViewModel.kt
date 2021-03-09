package ru.kbs41.kbsdatacollector.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository

//class MainViewModel(private val repository: AssemblyOrderRepository) : ViewModel() {
class MainViewModel() : ViewModel() {

    val repository = AssemblyOrderRepository()
    val allOrders: LiveData<List<AssemblyOrder>> = repository.allSortedAssemblyOrders.asLiveData()

    fun insert(assemblyOrder: AssemblyOrder) = viewModelScope.launch {
        repository.insert(assemblyOrder)
    }

}

/*
class MainViewModelFactory(private val repository: AssemblyOrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
 */