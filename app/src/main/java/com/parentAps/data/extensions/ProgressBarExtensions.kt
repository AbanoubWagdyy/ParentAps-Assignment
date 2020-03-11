package com.parentAps.data.extensions

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}