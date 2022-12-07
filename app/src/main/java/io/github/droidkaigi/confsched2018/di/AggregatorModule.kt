package io.github.droidkaigi.confsched2018.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2018.di.activitymodule.MainActivityBuilder
import io.github.droidkaigi.confsched2018.di.activitymodule.SessionDetailActivityBuilder
import io.github.droidkaigi.confsched2018.service.push.PushServiceBuilder

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        PushServiceBuilder::class,
        MainActivityBuilder::class,
        SessionDetailActivityBuilder::class,
    ]
)
interface AggregatorModule