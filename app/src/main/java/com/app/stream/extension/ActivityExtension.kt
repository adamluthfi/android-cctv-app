package com.app.stream.extension

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.app.stream.R

fun Activity.startActivitySlideRight(intent: Intent) {
    val options = ActivityOptionsCompat.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
    startActivity(intent, options.toBundle())
}