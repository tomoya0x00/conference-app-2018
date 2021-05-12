package io.github.droidkaigi.confsched2018.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module interface ViewModelModule {

    @Binds fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
