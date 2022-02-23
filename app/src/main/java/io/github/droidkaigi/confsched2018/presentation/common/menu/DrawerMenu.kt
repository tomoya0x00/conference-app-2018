package io.github.droidkaigi.confsched2018.presentation.common.menu

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import io.github.droidkaigi.confsched2018.R
import io.github.droidkaigi.confsched2018.presentation.MainActivity
import io.github.droidkaigi.confsched2018.presentation.NavigationController
import javax.inject.Inject
import kotlin.reflect.KClass

class DrawerMenu @Inject constructor(
    private val activity: AppCompatActivity,
    private val navigationController: NavigationController
) {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentNavigationItem: DrawerNavigationItem

    fun setup(
        drawerLayout: DrawerLayout,
        navigationView: NavigationView,
        toolbar: Toolbar? = null,
        actionBarDrawerSync: Boolean = false
    ) {
        this.drawerLayout = drawerLayout
        if (actionBarDrawerSync) {
            object : ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.nav_content_description_drawer_open,
                R.string.nav_content_description_drawer_close
            ) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    if (activity.currentFocus is SearchView.SearchAutoComplete) {
                        drawerView.requestFocus()
                    }
                }
            }.also {
                drawerLayout.addDrawerListener(it)
            }.apply {
                isDrawerIndicatorEnabled = true
                isDrawerSlideAnimationEnabled = false
                syncState()
            }
        }
        navigationView.setNavigationItemSelectedListener { item ->
            DrawerNavigationItem
                .values()
                .firstOrNull { it.menuId == item.itemId }
                ?.apply {
                    if (this != currentNavigationItem) {
                        navigate(navigationController)
                    }
                }
            drawerLayout.closeDrawers()
            false
        }

        currentNavigationItem = DrawerNavigationItem
            .values()
            .firstOrNull { activity::class == it.activityClass }
            ?.also {
                navigationView.setCheckedItem(it.menuId)
            }
            ?: DrawerNavigationItem.OTHER
    }

    fun closeDrawerIfNeeded(): Boolean {
        return if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
            false
        } else {
            true
        }
    }

    enum class DrawerNavigationItem(
        @IdRes val menuId: Int,
        val activityClass: KClass<*>,
        val navigate: NavigationController.() -> Unit
    ) {
        HOME(R.id.nav_item_home, MainActivity::class, {
            navigateToMainActivity()
        }),
        FEEDBACK(R.id.nav_item_all_feedback, Unit::class, {
            navigateToExternalBrowser("https://goo.gl/forms/Hjp54vk5P0VILcgf1")
        }),
        OTHER(0, Unit::class, {
            //do nothing
        })
    }
}
