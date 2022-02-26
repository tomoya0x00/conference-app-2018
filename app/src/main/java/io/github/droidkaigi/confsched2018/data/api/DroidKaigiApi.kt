package io.github.droidkaigi.confsched2018.data.api

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.data.api.response.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface DroidKaigiApi {
    @GET("sessionize/all.json")
    @CheckResult
    suspend fun getSessions(): Response
}
