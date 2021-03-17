package ru.kbs41.kbsdatacollector

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Debug.waitForDebugger
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster
import ru.kbs41.kbsdatacollector.room.dao.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder

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

        startExchange()
        observeDocuments()
        return START_STICKY
        //return super.onStartCommand(intent, flags, startId)
    }

    private fun observeDocuments() {

        assemblyOrderDao = App(context).database.assemblyOrderDao()

        val assemblyOrders: LiveData<List<AssemblyOrder>> =
            assemblyOrderDao.getAssemblyOrderCompleteNotSent().asLiveData()

        assemblyOrders.observeForever {

            it.forEach { item ->

                Log.d("ExchangerService", "new order for sending")

                ExchangeMaster().sendOrdersTo1C(item)

                item.isSent = true
                GlobalScope.launch(Dispatchers.IO) { assemblyOrderDao.update(item) }
            }

        }
    }


    private fun startExchange() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {

                //TODO: Это дебаг НАХ
                //return@launch

                SystemClock.sleep(10000)
                ExchangeMaster().getData(application)
                Log.d("ExchangerService", "Exchange in process")
            }
        }
    }
}