package ru.kbs41.kbsdatacollector.stateManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.ExchangerService
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import java.io.IOException

class BootCompleted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //ИНИЦИАЛИЗАЦИЯ ЭКЗЕМПЛЯРА БАЗЫ ДАННЫХ
        AppDatabase.getDatabase(context, null)

        try {

            //СТАРТ СЛУЖБЫ
            GlobalScope.launch {
                val service = Intent(context.applicationContext, ExchangerService::class.java)
                //delay(6000)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startService(service)
                } else {
                    ContextCompat.startForegroundService(context, service)
                }
            }

        } catch (e: IOException) {
            Log.d("1C_TO_APP", e.message!!)
        }


        Log.d("BOOT", "BOOT COMPLETED")
        Toast.makeText(context, "Service is started", Toast.LENGTH_SHORT).show()
    }
}