package com.example.hw_room.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getTextAndValidate(): CharSequence {
    return this.editText?.text
        ?.takeIf { it.isNotBlank() }
        ?.also {
            this.error = ""
        } ?: run {
        this.error = "Incorrect input"
    }.toString()
}