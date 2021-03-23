package ru.kbs41.kbsdatacollector

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.ui.mainactivity.MainActivity


object AppNotificationManager {

    private const val CHANNEL_ID = "1"
    private const val NOTIFICATION_ID = 1



    private lateinit var applicationContext: Context

    fun instance(_context: Context): AppNotificationManager {
        applicationContext = _context
        return this
    }


    private val notificationChannel: NotificationChannel? by lazy {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID, "Все уведомления",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Уведомления обо всём в программе"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(false)

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager!!.createNotificationChannel(channel)

            channel

        } else {
            null
        }

    }

    fun notifyUser() {

        GlobalScope.launch(Dispatchers.IO) {

            val resultIntent = arrayOf(Intent(applicationContext, MainActivity::class.java))
            val pendingIntent = PendingIntent.getActivities(
                applicationContext,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var builder: NotificationCompat.Builder

            builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationCompat.Builder(applicationContext, notificationChannel!!.id)
            } else {
                NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            }


            builder.setSmallIcon(R.drawable.ic_baseline_list_24)
                .setContentTitle("Новый заказ")
                .setContentText("Необходимо собрать новый заказ!")
                .setOngoing(true)
                .setSound(soundUri)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)


            NotificationManagerCompat.from(applicationContext)
                .notify(NOTIFICATION_ID, builder.build())
        }

    }
}
