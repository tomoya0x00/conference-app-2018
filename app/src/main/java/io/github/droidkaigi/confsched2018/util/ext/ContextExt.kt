package io.github.droidkaigi.confsched2018.util.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(this, drawableRes)!!
}
