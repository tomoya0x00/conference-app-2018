package io.github.droidkaigi.confsched2018.di

import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides

// Share RecycledViewPool between content fragments of ViewPager.
@Module class RecycledViewPoolModule {

    private val recycledViewPool = RecyclerView.RecycledViewPool()

    @Provides
    fun providesRecycledViewPool(): RecyclerView.RecycledViewPool = recycledViewPool
}
