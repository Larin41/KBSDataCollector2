package ru.kbs41.kbsdatacollector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import ru.kbs41.kbsdatacollector.room.AppDatabase

class BootCompleted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("HUIPIZDA", "onReceive")
        //ИНИЦИАЛИЗАЦИЯ ЭКЗЕМПЛЯРА БАЗЫ ДАННЫХ
        AppDatabase.getDatabase(context, null)
        Log.d("HUIPIZDA", "DB is OK")


        val service = Intent(context, ExchangerService::class.java)
        context.startService(service)

        Log.d("BOOT", "BOOT COMPLETED")
        Toast.makeText(context, "Service is started", Toast.LENGTH_SHORT).show()
    }
}