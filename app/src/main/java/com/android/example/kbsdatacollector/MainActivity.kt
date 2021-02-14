package com.android.example.kbsdatacollector

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.kbsdatacollector.room.AppDatabase
import com.android.example.kbsdatacollector.room.dao.ProductDao
import com.android.example.kbsdatacollector.room.db.Product
import com.android.example.kbsdatacollector.viewModels.ProductViewModel
import com.android.example.kbsdatacollector.viewModels.ProductViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ProductListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        productViewModel.allProducts.observe(this, Observer { products ->
            products.let { adapter.submitList(it) }
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            GlobalScope.launch {
                val product = Product(0, "New Penis", null, null, null, null, null, null)
                App().database.productDao().insert(product)
            }

        //AppDatabase.getDatabase(this, ).productDao().insert(product)
            //productDao.insert(product)
        //productViewModel.insert(product)

        }

    }

}