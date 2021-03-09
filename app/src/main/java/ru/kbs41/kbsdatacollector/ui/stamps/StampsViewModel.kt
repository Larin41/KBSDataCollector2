package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.SoundEffects
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableStampsWithProducts
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class StampsViewModel() : ViewModel() {

    private lateinit var context: Context

    private var currentRowTableGoodsId: Long = 0
    private var qty: Double = 0.0
    private var docId: Long = 0
    private var productId: Long = 0

    private val repository = AssemblyOrderFullRepository()

    //NOT LIVE DATA
    lateinit var currentProduct: Product
    lateinit var currentAssemblyOrder: AssemblyOrder
    lateinit var currentRowTableGoods: AssemblyOrderTableGoods

    //LIVE DATA
    lateinit var tableStampsWithProducts: LiveData<List<AssemblyOrderTableStampsWithProducts>>
    lateinit var tableGoods: LiveData<List<AssemblyOrderTableGoods>>
    lateinit var tableStamps: LiveData<List<AssemblyOrderTableStamps>>



    fun getQty(): Double {
        return qty
    }

    fun initProperties(
        _currentRowTableGoodsId: Long,
        _docId: Long,
        _productId: Long,
        _qty: Double,
        _context: Context
    ) {

        context = _context
        currentRowTableGoodsId = _currentRowTableGoodsId
        qty = _qty
        docId = _docId
        productId = _productId

        //NOT LIVE DATA
        currentProduct = repository.getProduct(productId)
        currentAssemblyOrder = repository.getAssemblyOrder(docId)
        currentRowTableGoods = repository.getOneRowTableGoodsWithProductById(currentRowTableGoodsId)


        //LIVE DATA
        tableGoods = repository.getAssemblyOrderTableGoods(docId).asLiveData()
        tableStamps = repository.getAssemblyOrderTableStamps(docId).asLiveData()
        tableStampsWithProducts =
            repository.getAssemblyOrderTableStampsByAssemblyOrderIdAndProductIdWithProducts(
                docId,
                productId
            ).asLiveData()



        //ОБСЕРВЕРЫ

        //ОБСЕРВЕР НА МАРКИ. ЕСЛИ КОЛИЧЕСТВО МАРОК ИЗМЕНИЛОСЬ,
        //ЗНАЧИТ КОЛЛЕКТЕД В ТАБЛИЦЕ ТОВАРОВ ТОЖЕ ДОЛЖНО ПОМЕНЯТЬСЯ
        //А ДАЛЕЕ ДОЛЖНО ПОМЕНЯТЬСЯ ЗНАЧЕНИЕ НА КОМПЛЕТЕД В САМОМ ЗАКАЗЕ НА СБОРКУ
        tableStamps.observeForever {

            val docTableStamps = tableStamps.value

            if (currentRowTableGoods == null || docTableStamps == null) {
                return@observeForever
            }

            val qtyCollected = docTableStamps.size.toDouble()

            val uTableGoods = AssemblyOrderTableGoods(
                currentRowTableGoods.id,
                currentRowTableGoods.sourceGuid,
                currentRowTableGoods.row,
                currentRowTableGoods.qty,
                qtyCollected,
                currentRowTableGoods.assemblyOrderId,
                currentRowTableGoods.productId
            )

            repository.updateTableGoods(uTableGoods)
            currentRowTableGoods = repository.getOneRowTableGoodsWithProductById(currentRowTableGoodsId)

        }

        //ПОСМОТРИМ ВСЁ ЛИ СОБРАЛИ И ЕСЛИ СОБРАЛИ, ТО СДЕЛАЕМ СОБРАННЫМ
        tableGoods.observeForever{
            if (it == null || currentAssemblyOrder == null){
                return@observeForever
            }


            var needToMakeCompleted = true
            it.forEach { item ->
                if (item.qty != item.qtyCollected) {
                    needToMakeCompleted = false
                }
            }

            if (needToMakeCompleted) {
                if (currentAssemblyOrder != null) {
                    val orderToUpdate = AssemblyOrder(
                        currentAssemblyOrder.id,
                        currentAssemblyOrder.guid,
                        currentAssemblyOrder.date,
                        currentAssemblyOrder.number,
                        currentAssemblyOrder.counterpart,
                        currentAssemblyOrder.comment + " собрано!",
                        true,
                        currentAssemblyOrder.isSent
                    )
                    repository.updateAssemblyOrder(orderToUpdate)
                    currentAssemblyOrder = repository.getAssemblyOrder(docId)
                }
            }
        }



    }

    suspend fun insertNewStamp(barcode: String) {

        if (tableStamps.value != null) {
            if (qty.compareTo(tableStampsWithProducts.value!!.size) <= 0) {
                GlobalScope.launch(Dispatchers.IO) { SoundEffects().playError(context) }
                return
            }
        }

        val newItem = AssemblyOrderTableStamps(
            0,
            barcode,
            currentAssemblyOrder.id,
            currentProduct.id
        )

        repository.insertAssemblyOrderTableStamps(newItem)
    }
}
