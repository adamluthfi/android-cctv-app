package com.app.stream.common.extension

import androidx.core.content.ContextCompat
import com.app.stream.R
import com.google.android.material.button.MaterialButton

fun MaterialButton.showLoading() {
    isEnabled = false
    text = " "
    icon = ContextCompat.getDrawable(context, R.drawable.loading_spinner)
}

fun MaterialButton.hideLoading(textValue: String) {
    isEnabled = true
    text = textValue
    icon = null
}