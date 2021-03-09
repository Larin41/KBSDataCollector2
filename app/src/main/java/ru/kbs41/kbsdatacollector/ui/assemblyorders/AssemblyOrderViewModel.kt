package ru.kbs41.kbsdatacollector.ui.assemblyorders

import androidx.lifecycle.*
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class AssemblyOrderViewModel() : ViewModel() {

    val repository = AssemblyOrderFullRepository()

    lateinit var currentAssemblyOrder: MutableLiveData<AssemblyOrder>

    lateinit var tableGoods: MutableLiveData<List<AssemblyOrderTableGoods>>

    lateinit var tableStamps: MutableLiveData<List<AssemblyOrderTableStamps>>

    lateinit var assemblyOrderTableGoodsWithProducts: MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>

    lateinit var assemblyOrderTableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableQtyQtyCollected: MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>


    fun fetchData(docId: Long) {

        tableQtyQtyCollected = repository.getTableGoodsWithStamps(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

        currentAssemblyOrder =
            repository.getAssemblyOrderFlow(docId).asLiveData() as MutableLiveData<AssemblyOrder>
        tableGoods = repository.getAssemblyOrderTableGoods(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoods>>
        tableStamps = repository.getAssemblyOrderTableStamps(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableStamps>>
        assemblyOrderTableGoodsWithProducts =
            repository.getAssemblyOrderTableGoodsWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>
        assemblyOrderTableStampsWithProducts =
            repository.getAssemblyOrderTableStampsWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>
        tableStampsWithProducts =
            repository.getAssemblyOrderTableStampsByAssemblyOrderIdWithProducts(docId)
                .asLiveData() as MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>


        /*
        tableGoods.observeForever {

            if (it == null || currentAssemblyOrder.value == null){
                return@observeForever
            }


            var needToMakeCompleted = true
            it.forEach { item ->
                if (item.qty != item.qtyCollected) {
                    needToMakeCompleted = false
                }
            }

            if (needToMakeCompleted) {
                val currentOrder = currentAssemblyOrder.value
                if (currentOrder != null) {
                    val orderToUpdate = AssemblyOrder(
                        currentOrder.id,
                        currentOrder.guid,
                        currentOrder.date,
                        currentOrder.number,
                        currentOrder.counterpart,
                        currentOrder.comment + " собрано!",
                        true,
                        currentOrder.isSent
                    )
                    repository.updateAssemblyOrder(orderToUpdate)
                }
            }
        }
        */
    }
}

