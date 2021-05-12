package io.github.droidkaigi.confsched2018.util.ext

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.view.WindowManager
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat

fun Context.displaySize(): Size {
    val point = Point()
    val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    manager.defaultDisplay.getSize(point)
    return Size(point.x, point.y)
}

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.bool(@BoolRes boolRes: Int): Boolean = resources.getBoolean(boolRes)

fun Context.integer(@IntegerRes integerRes: Int): Int = resources.getInteger(integerRes)

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(this, drawableRes)!!
}

data class Size(val width: Int, val height: Int)
