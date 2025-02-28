package com.example.autoopenwhatsapp

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class WhatsAppMonitorService : Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startWorker() // Inicia la verificación cada 5 minutos
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AutoOpenWhatsApp")
            .setContentText("Monitoreando nuevos mensajes...")
            .setSmallIcon(R.drawable.ic_notification) // Asegúrate de que existe o usa R.mipmap.ic_launcher
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIFICATION_ID, notification) // Mantiene el servicio en primer plano
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "WhatsApp Monitor",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }

    private fun startWorker() {
        val workRequest = PeriodicWorkRequestBuilder<OpenWhatsAppWorker>(5, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "check_whatsapp_messages",
            ExistingPeriodicWorkPolicy.KEEP, // No lo reinicia si ya está corriendo
            workRequest
        )
    }

    companion object {
        private const val CHANNEL_ID = "whatsapp_monitor_channel"
        private const val NOTIFICATION_ID = 1
    }
}
