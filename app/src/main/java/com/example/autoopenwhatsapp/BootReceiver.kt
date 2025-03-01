package com.example.autoopenwhatsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Dispositivo reiniciado, iniciando servicio...")

            val serviceIntent = Intent(context, BackgroundService::class.java)
            context.startForegroundService(serviceIntent) // Correcto para Android 8+
        }
    }
}
