package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.SoundEffects
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class AssemblyOrderViewModel() : ViewModel() {

    var docId: Long = 0
    var context: Context? = null

    val repository = AssemblyOrderFullRepository()

    lateinit var currentAssemblyOrder: AssemblyOrder

    lateinit var tableGoods: MutableLiveData<List<AssemblyOrderTableGoods>>

    lateinit var tableStamps: MutableLiveData<List<AssemblyOrderTableStamps>>

    lateinit var assemblyOrderTableGoodsWithProducts: MutableLiveData<List<AssemblyOrderTableGoodsWithProducts>>

    lateinit var assemblyOrderTableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableStampsWithProducts: MutableLiveData<List<AssemblyOrderTableStampsWithProducts>>

    lateinit var tableQtyQtyCollected: MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

    fun fetchData(_context: Context, _docId: Long) {

        docId = _docId
        context = _context


        //NOT LIVE DATA
        currentAssemblyOrder = repository.getAssemblyOrder(docId)


        //LIVE DATA
        tableQtyQtyCollected = repository.getTableGoodsWithStamps(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>

        tableGoods = repository.getAssemblyOrderTableGoodsFlow(docId)
            .asLiveData() as MutableLiveData<List<AssemblyOrderTableGoods>>
        tableStamps = repository.getAssemblyOrderTableStampsByDocIdFlow(docId)
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


    }

    fun completeOrder() {

        val list = repository.getAssemblyOrderTableGoods(docId)
        var isCollected = true

        list.forEach {
            if (it.qty != it.qtyCollected) {
                isCollected = false
            }
        }

        if (isCollected){
            currentAssemblyOrder.isCompleted = true
            repository.updateAssemblyOrder(currentAssemblyOrder)
        } else {
            Toast.makeText(context!!, "СОБРАН НЕ ВЕСЬ ТОВАР!", Toast.LENGTH_LONG).show()
            GlobalScope.launch(Dispatchers.Main) { SoundEffects().playError(context!!) }
        }

    }

}

