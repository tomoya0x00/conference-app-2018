package io.github.droidkaigi.confsched2018.util.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import org.reactivestreams.Publisher

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>
