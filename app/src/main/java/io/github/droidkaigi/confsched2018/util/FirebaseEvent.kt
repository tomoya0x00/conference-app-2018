package io.github.droidkaigi.confsched2018.util

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseEvent @Inject constructor(val context: Context) : LifecycleObserver {

    private var fireBaseAnalytics: FirebaseAnalytics? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        fireBaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun sendEvent(params: Bundle) {
        fireBaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
    }

    class Builder(val bundle: Bundle = Bundle()) {

        fun put(key: String, value: String): Builder {
            bundle.putString(key, value)
            return this
        }

        fun build(): Bundle = Bundle(bundle)
    }
}
