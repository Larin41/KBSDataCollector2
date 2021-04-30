package ru.kbs41.kbsdatacollector

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.stateManager.NetworkChanging
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster


class ExchangerService : Service() {

    private lateinit var context: Context

    private lateinit var assemblyOrderDao: AssemblyOrderDao

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
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

        assemblyOrderDao = App(context).database.assemblyOrderDao()

        val assemblyOrders: LiveData<List<AssemblyOrder>> =
            assemblyOrderDao.getAssemblyOrderCompleteNotSentFlow().asLiveData()

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
                //TODO: debug
                //return@launch

                SystemClock.sleep(10000)

                ExchangeMaster.getData(application)
                Log.d("ExchangerService", "Exchange in process")
            }
        }
    }
}