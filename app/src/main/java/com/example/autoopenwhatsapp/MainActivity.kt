package com.example.autoopenwhatsapp

import android.content.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)
        val accessibilityButton = findViewById<Button>(R.id.accessibilityButton)
        val serviceIntent = Intent(this, WhatsAppMonitorService::class.java)

        startButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent) // Para Android 8+
            } else {
                startService(serviceIntent)
            }
        }
        // El evento de salir//
        stopButton.setOnClickListener {
            stopService(serviceIntent)
        }

        // Pedir permiso para ejecución en segundo plano
        showPermissionDialog()

        // Iniciar el servicio al presionar el botón
        startButton.setOnClickListener {
            startBackgroundService()
        }

        // Detener el servicio al presionar el botón
        stopButton.setOnClickListener {
            stopBackgroundService()
        }

        // Abrir ajustes de accesibilidad
        accessibilityButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permiso necesario")
            .setMessage("¿Quieres permitir que la aplicación se ejecute en segundo plano?")
            .setPositiveButton("Yes") { _, _ -> startBackgroundService() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun startBackgroundService() {
        val intent = Intent(this, BackgroundService::class.java)
        startForegroundService(intent)
    }

    private fun stopBackgroundService() {
        val intent = Intent(this, BackgroundService::class.java)
        stopService(intent)
    }
}
