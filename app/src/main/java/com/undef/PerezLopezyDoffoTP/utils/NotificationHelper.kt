package com.undef.PerezLopezyDoffoTP.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.undef.PerezLopezyDoffoTP.R

object NotificationHelper {

    private const val CHANNEL_ID = "favorite_alerts_channel"
    private const val CHANNEL_NAME = "Alertas de emprendedores"
    private const val CHANNEL_DESC = "Notificaciones cuando hay novedades"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESC
            }

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun showActivatedNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon) // tu icono
            .setContentTitle("Alertas activadas")
            .setContentText("¡Ahora recibirás alertas cada vez que el emprendedor suba un nuevo producto!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notification)
    }
}
