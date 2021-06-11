package ru.kbs41.kbsdatacollector.stateManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.ExchangerService
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster
import java.lang.Exception

class NetworkChanging : BroadcastReceiver() {

    private lateinit var service: Intent


    override fun onReceive(context: Context?, intent: Intent?) {

        service = Intent(context, ExchangerService::class.java)

        if (isOnline(context!!)) {
          ExchangeMaster.sendAllOrdersTo1C()
          ExchangeMaster.sendAllSimpleScanningTo1C()
        }

    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
