package com.ajsherrell.android.quiz_hyperbaric.utils

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun ObservableField<String>.getOrEmpty(): String = this.get() ?: ""