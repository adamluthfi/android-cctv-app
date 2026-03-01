package com.app.stream.ui.common.loading

interface LoadingController {
    fun show(message: String? = null)
    fun hide()
    fun isShowing(): Boolean
}
