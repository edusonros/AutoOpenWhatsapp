package com.example.autoopenwhatsapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            Log.d("MyAccessibilityService", "Evento de accesibilidad detectado: ${event.eventType}")

            if (event.packageName == "com.whatsapp") {
                Log.d("MyAccessibilityService", "Interacción con WhatsApp detectada.")
                // Aquí puedes analizar el contenido del evento y reaccionar en consecuencia
            }
        }
    }

    override fun onInterrupt() {
        Log.d("MyAccessibilityService", "Servicio de accesibilidad interrumpido.")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("MyAccessibilityService", "Servicio de accesibilidad conectado.")

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED or AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            packageNames = arrayOf("com.whatsapp") // Solo escuchar eventos de WhatsApp
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
        serviceInfo = info
    }
}
