package ru.kbs41.kbsdatacollector

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.dataBase.contractors.Contractor
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.dataSources.dataBase.repository.ProductRepository
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningTableGoods
import ru.kbs41.kbsdatacollector.notificationManager.AppNotificationManager
import java.util.*

class App(_context: Context? = null) : Application() {

    var sContext = _context

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {
        AppDatabase.getDatabase(getCurrentContext(), applicationScope)
    }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    //val assemblyOrdersRepository by lazy { AssemblyOrderRepository(database.assemblyOrderDao()) }

    fun getCurrentContext(): Context {
        if (sContext != null) {
            return sContext as Context
        } else {
            return this
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppNotificationManager.instance(applicationContext)
        createPredefinedData()
    }

    private fun createPredefinedData() {

        GlobalScope.launch {

            val contractorDao = database.contractorDao()
            var contractor = contractorDao.findContractorByGuid(Constants.RETAIL_GUID)

            if (contractor == null) {
                contractor = Contractor()
                contractor.guid = Constants.RETAIL_GUID
                contractor.name = "Розничный покупатель"

                contractorDao.insert(contractor)
            }


        }


    }

}

