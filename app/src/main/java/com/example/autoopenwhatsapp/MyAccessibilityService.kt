package com.example.autoopenwhatsapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityEvent
import android.content.Context
import android.os.PowerManager
import android.widget.Toast

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName.toString() == "com.whatsapp" &&
            event.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {

            wakeUpScreen()
            showMessage()
        }
    }

    override fun onInterrupt() {}

    private fun wakeUpScreen() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire(3000) // Mantener la pantalla encendida por 3 segundos
        wakeLock.release()
    }

    private fun showMessage() {
        Toast.makeText(this, "📩 Tienes mensajes de WhatsApp nuevos", Toast.LENGTH_LONG).show()
    }
}
