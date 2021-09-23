package io.github.droidkaigi.confsched2018.presentation.common.binding

import android.content.res.ColorStateList

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("tintCompat")
fun FloatingActionButton.setTintCompat(colorStateList: ColorStateList) {
    var icon: Drawable? = drawable
    if (icon != null) {
        icon = DrawableCompat.wrap(icon)
        DrawableCompat.setTintList(icon, colorStateList)
        setImageDrawable(icon)
    }
}
