package com.example.tvapplocker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LockScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val btnUnlock = findViewById<Button>(R.id.btnUnlock)
        val btnHome = findViewById<Button>(R.id.btnHome)

        tvMessage.text = "App is locked!\nCurfew is active from 10:00 PM to 7:00 AM."

        btnUnlock.setOnClickListener {
            val intent = Intent(this, PinActivity::class.java)
            startActivityForResult(intent, 100)
        }

        btnHome.setOnClickListener {
            goToHome()
        }

        btnUnlock.requestFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            AppLockService.isUnlockedTemporarily = true
            finish()
        }
    }

    private fun goToHome() {
        val startMain = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(startMain)
        finish()
    }

    override fun onBackPressed() {
        goToHome()
    }
}
