package io.github.droidkaigi.confsched2018.util

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils


object ColorCalculator {

    @JvmStatic
    fun calculateColor(fraction: Float, @ColorInt color: Int): Int {
        return ColorUtils.setAlphaComponent(color, (255 * fraction).toInt())
    }
}
