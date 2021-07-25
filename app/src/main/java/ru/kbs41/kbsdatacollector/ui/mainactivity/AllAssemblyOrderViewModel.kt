package ru.kbs41.kbsdatacollector.ui

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData.AssemblyOrdersWithContractors
import ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData.RawQuery
import ru.kbs41.kbsdatacollector.dataSources.dataBase.repository.AssemblyOrderRepository
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity
import java.util.*

//class MainViewModel(private val repository: AssemblyOrderRepository) : ViewModel() {
class MainViewModel() : ViewModel() {

    val repository = AssemblyOrderRepository()
    //val allOrders: LiveData<List<AssemblyOrder>> = repository.allSortedAssemblyOrders.asLiveData()
    val allOrders: LiveData<List<AssemblyOrdersWithContractors>> = RawQuery.getNotCompletedAssemblyOrders().asLiveData()

    val database = App().database
    val assemblyOrderDao = database.assemblyOrderDao()

    fun insert(assemblyOrder: AssemblyOrder) = viewModelScope.launch {
        repository.insert(assemblyOrder)
    }

    suspend fun addNewAssemblyOrder() : Long {

        val newAssemblyOrder = AssemblyOrder()
        newAssemblyOrder.date = Date()
        newAssemblyOrder.comment = ""
        newAssemblyOrder.counterpart = ""
        newAssemblyOrder.isCompleted = false
        newAssemblyOrder.isSent = false
        newAssemblyOrder.number = ""

        return assemblyOrderDao.insert(newAssemblyOrder)

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