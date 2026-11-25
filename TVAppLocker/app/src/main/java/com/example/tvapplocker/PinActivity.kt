package com.example.tvapplocker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        val etPin = findViewById<EditText>(R.id.etPin)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val enteredPin = etPin.text.toString()
            val prefs = getSharedPreferences("TVLockerPrefs", Context.MODE_PRIVATE)
            val savedPin = prefs.getString("pin_code", "1234") // default PIN is 1234

            if (enteredPin == savedPin) {
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            }
        }
        etPin.requestFocus()
    }
}
