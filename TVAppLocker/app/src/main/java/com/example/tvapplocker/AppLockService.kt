package com.example.tvapplocker

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.content.Context
import java.util.Calendar

class AppLockService : AccessibilityService() {

    companion object {
        var isUnlockedTemporarily = false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return

            // Ignore self
            if (packageName == "com.example.tvapplocker") return

            val prefs = getSharedPreferences("TVLockerPrefs", Context.MODE_PRIVATE)
            val blockedApps = prefs.getStringSet("blocked_apps", setOf("com.google.android.youtube.tv", "com.google.android.youtube")) ?: emptySet()

            if (blockedApps.contains(packageName)) {
                if (!isUnlockedTemporarily && isInCurfew()) {
                    // Redirect to lock screen
                    val intent = Intent(this, LockScreenActivity::class.java).apply {
                        putExtra("locked_package", packageName)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun isInCurfew(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        // Curfew: 22:00 (10 PM) to 07:00 (7 AM)
        return hour >= 22 || hour < 7
    }

    override fun onInterrupt() {}
}
