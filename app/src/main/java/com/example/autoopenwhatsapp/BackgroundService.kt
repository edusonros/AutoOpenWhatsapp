package com.example.autoopenwhatsapp

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.work.*
import java.util.concurrent.TimeUnit

class BackgroundService : Service() {
    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification())

        // Iniciar el Worker para verificar mensajes cada 5 minutos
        val workRequest = PeriodicWorkRequestBuilder<OpenWhatsAppWorker>(5, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "check_whatsapp_messages",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        val channelId = "BackgroundServiceChannel"
        val channel = NotificationChannel(
            channelId, "Servicio en Segundo Plano", NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return Notification.Builder(this, channelId)
            .setContentTitle("AutoOpenWhatsApp")
            .setContentText("Ejecutándose en segundo plano...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }
}
