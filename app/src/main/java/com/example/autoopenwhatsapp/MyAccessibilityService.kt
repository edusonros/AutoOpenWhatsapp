package com.example.autoopenwhatsapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.widget.Toast

class MyAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 30000 // Revisar cada 30 segundos

    override fun onServiceConnected() {
        super.onServiceConnected()
        startCheckingMessages()
    }

    private fun startCheckingMessages() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                checkForWhatsAppMessages()
                handler.postDelayed(this, checkInterval)
            }
        }, checkInterval)
    }

    private fun checkForWhatsAppMessages() {
        showMessage()
        wakeUpScreen()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            if (it.packageName?.toString() == "com.whatsapp" &&
                it.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                wakeUpScreen()
                showMessage()
            }
        }
    }

    override fun onInterrupt() {}

    private fun wakeUpScreen() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire(3000)
        wakeLock.release()
    }

    private fun showMessage() {
        Toast.makeText(this, "📩 Tienes mensajes de WhatsApp nuevos", Toast.LENGTH_LONG).show()
    }
}
