package ru.kbs41.kbsdatacollector.stateManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.kbs41.kbsdatacollector.ExchangerService
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase

class BootCompleted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //ИНИЦИАЛИЗАЦИЯ ЭКЗЕМПЛЯРА БАЗЫ ДАННЫХ
        AppDatabase.getDatabase(context, null)

        val service = Intent(context, ExchangerService::class.java)
        context.startService(service)
        //ContextCompat.startForegroundService(context, service)


        Log.d("BOOT", "BOOT COMPLETED")
        Toast.makeText(context, "Service is started", Toast.LENGTH_SHORT).show()
    }
}