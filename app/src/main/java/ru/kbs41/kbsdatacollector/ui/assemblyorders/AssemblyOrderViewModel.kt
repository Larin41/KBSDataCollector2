package ru.kbs41.kbsdatacollector.ui.assemblyorders

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class AssemblyOrderViewModel() : ViewModel() {

    val repository = AssemblyOrderFullRepository()

    lateinit var assemblyOrders: MutableLiveData<List<AssemblyOrder>>

    lateinit var tableGoods: MutableLiveData<List<AssemblyOrderTableGoods>>

    lateinit var tableStamps: MutableLiveData<List<AssemblyOrderTableStamps>>

    lateinit var assemblyOrderTableGoodsWithProducts: MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>

    lateinit var assemblyOrderTableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableQtyQtyCollected: MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>


    fun initProperties(docId: Long) {

        tableQtyQtyCollected = repository.getTest(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

        assemblyOrders = repository.getAssemblyOrder(docId).asLiveData() as MutableLiveData<List<AssemblyOrder>>
        tableGoods = repository.getAssemblyOrderTableGoods(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableGoods>>
        tableStamps = repository.getAssemblyOrderTableStamps(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableStamps>>
        assemblyOrderTableGoodsWithProducts = repository.getAssemblyOrderTableGoodsWithProducts(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>
        assemblyOrderTableStampsWithProducts = repository.getAssemblyOrderTableStampsWithProducts(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>
        tableStampsWithProducts = repository.getAssemblyOrderTableStampsByAssemblyOrderIdWithProducts(docId).asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    }

}

/*
class AssemblyOrderViewModelFactory(private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssemblyOrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssemblyOrderViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/
