package ru.kbs41.kbsdatacollector

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.dataBase.repository.ProductRepository
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningTableGoods
import ru.kbs41.kbsdatacollector.notificationManager.AppNotificationManager
import java.util.*

class App(_context: Context? = null) : Application() {

    var context = _context

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(getCurrentContext(), applicationScope) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    //val assemblyOrdersRepository by lazy { AssemblyOrderRepository(database.assemblyOrderDao()) }

    fun getCurrentContext(): Context {
        if (context != null) {
            return context as Context
        } else {
            return this
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppNotificationManager.instance(applicationContext)
        //createTestData()
    }

    private fun createTestData() {

        GlobalScope.launch {
            val simpleScanningDao = database.simpleScanningDao()
            val simpleScanningTableGoodsDao = database.simpleScanningTableGoodsDao()

            val simpleScanning = SimpleScanning(
                0,
                "jdnlksjandlkjnaslk",
                Date(),
                "Test comment yeah!",
                false,
                false
            )
            val docId = simpleScanningDao.insert(simpleScanning)

            val list = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

            list.forEach {
                val simpleScanningTableGoods = SimpleScanningTableGoods(
                    0,
                    "guid $it",
                    it.toDouble(),
                    docId,
                    it.toLong()
                )

                simpleScanningTableGoodsDao.insert(simpleScanningTableGoods)

            }


        }

    }

}