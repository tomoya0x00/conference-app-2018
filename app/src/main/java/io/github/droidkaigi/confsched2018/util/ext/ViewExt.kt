package io.github.droidkaigi.confsched2018.util.ext

import android.view.View

fun View.setVisible(visible: Boolean) {
    if (visible) {
        toVisible()
    } else {
        toGone()
    }
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.isGone() = visibility == View.GONE

fun View.isLayoutDirectionRtl() =
    resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
