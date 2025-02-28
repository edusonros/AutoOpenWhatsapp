package com.example.autoopenwhatsapp

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.work.Worker
import androidx.work.WorkerParameters

class OpenWhatsAppWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // Aquí podríamos verificar notificaciones de WhatsApp con NotificationListenerService (avanzado)
        // Como alternativa, abrimos WhatsApp directamente cada 5 minutos
        openWhatsApp(applicationContext)

        return Result.success()
    }

    private fun openWhatsApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.whatsapp")
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        // Cerrar WhatsApp después de 20 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val closeIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            context.sendBroadcast(closeIntent)
        }, 20000)
    }
}
