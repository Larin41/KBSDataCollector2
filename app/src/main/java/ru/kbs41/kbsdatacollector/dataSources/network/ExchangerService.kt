package ru.kbs41.kbsdatacollector

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Debug
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.stateManager.NetworkChanging
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster


class ExchangerService : Service() {

    val context = this
    val database = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val simpleScanningDao = database.simpleScanningDao()


//    private lateinit var context: Context
//
//    private lateinit var assemblyOrderDao: AssemblyOrderDao
//    private lateinit var assemblyOrderDao: AssemblyOrderDao

    override fun onCreate() {
        super.onCreate()
        //context = applicationContext
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //TODO: УДАЛИТЬ ДЕБАГГЕР
        //waitForDebugger()
        registerReceiver(NetworkChanging(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        startExchange()
        observeDocuments()
        return START_STICKY
        //return super.onStartCommand(intent, flags, startId)
    }

    private fun observeDocuments() {

        observeAssemblyOrders()
        observeSimpleScanning()

    }

    private fun observeSimpleScanning() {

        val simpleScanning = simpleScanningDao.getCompletedNotSendedLiveData()

        simpleScanning.observeForever {
            it.forEach { item ->
                Log.d("SimpleScanning_TO_1C", "new scanning for sending")
                GlobalScope.launch(Dispatchers.IO) {
                    //Debug.waitForDebugger()
                    ExchangeMaster.sendSimpleScanningTo1C(item)
                }
            }
        }
    }

    private fun observeAssemblyOrders() {

        val assemblyOrders = assemblyOrderDao.getCompletedNotSendedLiveData()

        assemblyOrders.observeForever {
            it.forEach { item ->
                Log.d("ExchangerService", "new order for sending")
                GlobalScope.launch(Dispatchers.IO) { ExchangeMaster.sendOrderTo1C(item) }
            }
        }
    }


    private fun startExchange() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                SystemClock.sleep(60000)
                ExchangeMaster.getOrdersFrom1C(application)
                Log.d("ExchangerService", "Exchange in process")
            }
        }
    }
}