package com.madeit.shooters.common

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}