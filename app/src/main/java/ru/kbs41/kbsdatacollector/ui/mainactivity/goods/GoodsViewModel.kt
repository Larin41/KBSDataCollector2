package ru.kbs41.kbsdatacollector.ui.mainactivity.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product

class GoodsViewModel : ViewModel() {

    val database = App().database
    val productDao = database.productDao()

    val allProducts: LiveData<List<Product>> = productDao.getAllFlow().asLiveData()


}