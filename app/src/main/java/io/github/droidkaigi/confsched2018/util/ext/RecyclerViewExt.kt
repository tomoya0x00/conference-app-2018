package io.github.droidkaigi.confsched2018.util.ext

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


inline fun RecyclerView.addOnScrollListener(
        crossinline onScrollStateChanged: (recyclerView: RecyclerView?, newState: Int) -> Unit =
        { _, _ -> },
        crossinline onScrolled: (recyclerView: RecyclerView?, dx: Int, dy: Int) -> Unit =
        { _, _, _ -> }
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(recyclerView, dx, dy)
        }
    })
}

fun RecyclerView.setLinearDivider(
        @DrawableRes drawableResId: Int,
        linearLayoutManager: LinearLayoutManager
) {
    val context = this.context
    this.addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(ContextCompat.getDrawable(context, drawableResId)!!)
    })
}
