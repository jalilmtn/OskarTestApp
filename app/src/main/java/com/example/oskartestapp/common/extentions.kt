package com.example.oskartestapp.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import androidx.core.content.ContextCompat

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Should be called in the context of an Activity")
}

fun Activity.clearAppData() {
    val activityService =
        ContextCompat.getSystemService(this, ActivityManager::class.java)
    activityService?.clearApplicationUserData()
}