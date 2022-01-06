package com.sliide.app.util.extension

import androidx.core.util.PatternsCompat

fun String?.isValidEmail(): Boolean {
    return if (this.isNullOrBlank())
        return false
    else PatternsCompat.EMAIL_ADDRESS
        .matcher(this)
        .matches()
}