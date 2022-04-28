package com.example.hw_room.utils

import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.getTextOrNullAndValidate(): CharSequence? {
    return this.editText?.text
        ?.takeIf { it.isNotBlank() }
        ?.also {
            this.error = ""
        } ?: run {
        this.error = "Incorrect input"
        null
    }
}

fun String.toFirstNameStringFormat(): String {
    return "first name:\n$this"
}

fun String.toLastNameStringFormat(): String {
    return "last name:\n$this"
}
