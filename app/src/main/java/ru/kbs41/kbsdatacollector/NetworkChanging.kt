package ru.kbs41.kbsdatacollector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster


class NetworkChanging : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (isOnline(context!!)) {
            ExchangeMaster().sendAllOrdersTo1C()
        }

    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
