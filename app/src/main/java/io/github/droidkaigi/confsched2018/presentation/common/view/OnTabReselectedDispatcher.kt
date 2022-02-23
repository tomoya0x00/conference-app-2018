package io.github.droidkaigi.confsched2018.presentation.common.view

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class OnTabReselectedDispatcher(
    private val viewPager: ViewPager
) : TabLayout.OnTabSelectedListener {

    override fun onTabUnselected(tab: TabLayout.Tab) {
        // no-op
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        // no-op
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        val item = viewPager.adapter?.instantiateItem(viewPager, tab.position)
        if (item is OnTabReselectedListener) {
            item.onTabReselected()
        }
    }
}

interface OnTabReselectedListener {
    fun onTabReselected()
}
