package io.github.droidkaigi.confsched2018.data.db

import androidx.annotation.CheckResult
import io.github.droidkaigi.confsched2018.model.Session
import kotlinx.coroutines.flow.SharedFlow

interface FavoriteDatabase {

    @get:CheckResult val favorites: SharedFlow<List<Int>>

    @CheckResult suspend fun favorite(session: Session): Boolean
}
