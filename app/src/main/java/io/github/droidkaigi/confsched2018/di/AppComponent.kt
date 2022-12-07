package io.github.droidkaigi.confsched2018.di

import dagger.android.AndroidInjector
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2018.presentation.App

@InstallIn(SingletonComponent::class)
@EntryPoint
interface AppComponent : AndroidInjector<App> {
    override fun inject(app: App)
}
