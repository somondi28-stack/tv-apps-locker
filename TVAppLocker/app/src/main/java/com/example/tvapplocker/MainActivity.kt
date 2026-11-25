package com.example.tvapplocker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cbYoutube = findViewById<CheckBox>(R.id.cbYoutube)
        val cbYoutubeTv = findViewById<CheckBox>(R.id.cbYoutubeTv)
        val cbNetflix = findViewById<CheckBox>(R.id.cbNetflix)
        val etNewPin = findViewById<EditText>(R.id.etNewPin)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnAccessibility = findViewById<Button>(R.id.btnAccessibility)

        val prefs = getSharedPreferences("TVLockerPrefs", Context.MODE_PRIVATE)
        val blockedApps = prefs.getStringSet("blocked_apps", setOf("com.google.android.youtube.tv", "com.google.android.youtube")) ?: emptySet()

        cbYoutube.isChecked = blockedApps.contains("com.google.android.youtube")
        cbYoutubeTv.isChecked = blockedApps.contains("com.google.android.youtube.tv")
        cbNetflix.isChecked = blockedApps.contains("com.netflix.ninja")

        btnSave.setOnClickListener {
            val newBlocked = mutableSetOf<String>()
            if (cbYoutube.isChecked) newBlocked.add("com.google.android.youtube")
            if (cbYoutubeTv.isChecked) newBlocked.add("com.google.android.youtube.tv")
            if (cbNetflix.isChecked) newBlocked.add("com.netflix.ninja")

            val editor = prefs.edit()
            editor.putStringSet("blocked_apps", newBlocked)

            val pinText = etNewPin.text.toString()
            if (pinText.isNotEmpty()) {
                if (pinText.length == 4) {
                    editor.putString("pin_code", pinText)
                } else {
                    Toast.makeText(this, "PIN must be 4 digits", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            editor.apply()
            Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show()
        }

        btnAccessibility.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        btnAccessibility.requestFocus()
    }
}
