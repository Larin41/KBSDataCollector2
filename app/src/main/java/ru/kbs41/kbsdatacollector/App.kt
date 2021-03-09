package ru.kbs41.kbsdatacollector

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository
import ru.kbs41.kbsdatacollector.room.repository.ProductRepository

class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy{ AppDatabase.getDatabase(this, applicationScope) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    //val assemblyOrdersRepository by lazy { AssemblyOrderRepository(database.assemblyOrderDao()) }
}