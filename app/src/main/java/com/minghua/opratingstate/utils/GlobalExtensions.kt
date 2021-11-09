package com.minghua.opratingstate.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

private tailrec fun Context.getCurrentActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getCurrentActivity()
    else -> null
}
val Context.activity: AppCompatActivity? get() = getCurrentActivity()