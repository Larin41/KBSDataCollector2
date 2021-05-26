package ru.kbs41.kbsdatacollector.stateManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster


class NetworkChanging : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (isOnline(context!!)) {
            GlobalScope.launch(Dispatchers.IO) {
                ExchangeMaster.sendAllOrdersTo1C()
                ExchangeMaster.sendAllSimpleScanningTo1C()
            }
        }

    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
