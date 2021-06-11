package ru.kbs41.kbsdatacollector

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Debug
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningDao
import ru.kbs41.kbsdatacollector.stateManager.NetworkChanging
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster
import ru.kbs41.kbsdatacollector.ui.mainactivity.MainActivity


class ExchangerService : Service() {

    val context = this
    private lateinit var database: AppDatabase
    private lateinit var assemblyOrderDao: AssemblyOrderDao
    private lateinit var simpleScanningDao: SimpleScanningDao
    private val CHANNAL_ID = "KBS_DATA_COLLECTOR"
    private val NOTIFICATION_ID = 666
    private lateinit var assemblyOrders: LiveData<List<AssemblyOrder>>
    private lateinit var simpleScanning: LiveData<List<SimpleScanning>>


    override fun onCreate() {
        super.onCreate()
        database = App(this).database

        assemblyOrderDao = database.assemblyOrderDao()
        simpleScanningDao = database.simpleScanningDao()

        fetchLiveData()
        subscribeToObservers()
        registerReceiver(NetworkChanging(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        startExchange()

        createNotificationChannal()

    }

    private fun createNotificationChannal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNAL_ID, "KBS_DATA_COLLECTOR", NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)

        }
    }


    private fun fetchLiveData() {
        assemblyOrders = assemblyOrderDao.getCompletedNotSendedLiveData()
        simpleScanning = simpleScanningDao.getCompletedNotSendedLiveData()
    }

    private fun subscribeToObservers() {

        simpleScanning.observeForever {
            it.forEach { item ->
                Log.d("SimpleScanning_TO_1C", "new scanning for sending")
                GlobalScope.launch(Dispatchers.IO) {
                    //Debug.waitForDebugger()
                    ExchangeMaster.sendSimpleScanningTo1C(item)
                }
            }
        }

        assemblyOrders.observeForever {
            it.forEach { item ->
                //Debug.waitForDebugger()
                Log.d("ExchangerService", "new order for sending")
                GlobalScope.launch(Dispatchers.IO) { ExchangeMaster.sendOrderTo1C(item) }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        return START_STICKY
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationIntent = Intent(this, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

            val notification = Notification
                .Builder(this, CHANNAL_ID)
                .setSmallIcon(R.drawable.ic_baseline_domain_verification_24)
                .setContentIntent(pendingIntent)
                .build()

            startForeground(NOTIFICATION_ID, notification)
        }

    }


    private fun startExchange() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                SystemClock.sleep(10000)
                ExchangeMaster.getOrdersFrom1C(application)
                Log.d("ExchangerService", "Exchange in process")
            }
        }
    }
}