package io.github.droidkaigi.confsched2018.test.data.api

import androidx.annotation.CheckResult
import com.squareup.moshi.Moshi
import io.github.droidkaigi.confsched2018.data.api.DroidKaigiApi
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.github.droidkaigi.confsched2018.data.api.response.mapper.ApplicationJsonAdapterFactory
import io.github.droidkaigi.confsched2018.data.api.response.mapper.InstantAdapter
import io.reactivex.Single
import org.threeten.bp.Instant

class StubDroidKaigiApi : DroidKaigiApi {
    @CheckResult override fun getSessions(): Single<Response> = Single.create { emitter ->
        val json = StubDroidKaigiApi::class.java.classLoader.getResourceAsStream("all.json")
            .bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder()
            .add(ApplicationJsonAdapterFactory)
            .add(Instant::class.java, InstantAdapter())
            .build()
        val adapter = moshi.adapter(Response::class.java)
        val response = adapter.fromJson(json)
        emitter.onSuccess(response!!)
    }
}
