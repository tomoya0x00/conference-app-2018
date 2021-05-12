package io.github.droidkaigi.confsched2018.data.api

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface DroidKaigiApi {
    @GET("sessionize/all.json")
    @CheckResult
    fun getSessions(): Single<Response>
}
