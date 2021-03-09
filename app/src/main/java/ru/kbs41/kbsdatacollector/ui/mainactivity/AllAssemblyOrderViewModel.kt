package ru.kbs41.kbsdatacollector.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository

class MainViewModel() : ViewModel() {

    private val repository = AssemblyOrderRepository()
    val allOrders: LiveData<List<AssemblyOrder>> = repository.allSortedAssemblyOrders.asLiveData()


}
