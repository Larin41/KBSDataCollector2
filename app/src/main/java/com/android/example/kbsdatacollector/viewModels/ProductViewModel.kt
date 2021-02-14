package com.android.example.kbsdatacollector.viewModels

import androidx.lifecycle.*
import com.android.example.kbsdatacollector.room.db.Product
import com.android.example.kbsdatacollector.room.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val allProducts: LiveData<List<Product>> = repository.allProducts.asLiveData()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}