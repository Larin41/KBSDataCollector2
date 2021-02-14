package com.android.example.kbsdatacollector

import android.app.Application
import com.android.example.kbsdatacollector.room.AppDatabase
import com.android.example.kbsdatacollector.room.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ProductRepository(database.productDao()) }
}