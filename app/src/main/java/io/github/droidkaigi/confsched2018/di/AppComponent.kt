package io.github.droidkaigi.confsched2018.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.droidkaigi.confsched2018.di.activitymodule.MainActivityBuilder
import io.github.droidkaigi.confsched2018.di.activitymodule.SessionDetailActivityBuilder
import io.github.droidkaigi.confsched2018.presentation.App
import io.github.droidkaigi.confsched2018.service.push.PushServiceBuilder
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        PushServiceBuilder::class,
        MainActivityBuilder::class,
        SessionDetailActivityBuilder::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}
