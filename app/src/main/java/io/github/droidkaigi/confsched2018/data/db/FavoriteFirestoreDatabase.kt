package io.github.droidkaigi.confsched2018.data.db

import androidx.annotation.CheckResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.util.FirebaseAuthKtx
import io.github.droidkaigi.confsched2018.util.ext.adds
import io.github.droidkaigi.confsched2018.util.ext.deletes
import io.github.droidkaigi.confsched2018.util.ext.getsSnapshot
import io.github.droidkaigi.confsched2018.util.ext.isEmpty
import io.github.droidkaigi.confsched2018.util.ext.observesSnapshot
import io.github.droidkaigi.confsched2018.util.ext.sets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class FavoriteFirestoreDatabase : FavoriteDatabase {

    private var isInitialized = false

    // Injecting by Dagger is preferable
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @CheckResult override suspend fun favorite(session: Session): Boolean {
        if (!isInitialized) {
            throw NotPreparedException()
        }

        val user = FirebaseAuthKtx.getCurrentUser()
        val document = favoritesRef(user).document(session.id).getsSnapshot()
        val nowFavorite = document.exists() && (document.data?.get(session.id) == true)
        val newFavorite = !nowFavorite

        if (document.exists()) {
            document.reference
                .deletes()
        } else {
            document.reference
                .sets(mapOf("favorite" to newFavorite))
        }

        return newFavorite
    }

    @get:CheckResult
    override val favorites: SharedFlow<List<Int>> =
        flow {
            val user = FirebaseAuthKtx.getCurrentUser()
            setupFavoritesDocument(user)
            emit(user)
        }.flatMapLatest { user ->
            getFavorites(user)
        }.onEach { isInitialized = true }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed()
            )

    @CheckResult
    private suspend fun setupFavoritesDocument(currentUser: FirebaseUser) {
        val favorites = favoritesRef(currentUser)

        if (favorites.isEmpty()) {
            favorites.adds(mapOf("initialized" to true))
        }
    }

    @CheckResult private fun getFavorites(currentUser: FirebaseUser): Flow<List<Int>> =
        flow {
            val query = favoritesRef(currentUser)
                .whereEqualTo("favorite", true)
            emit(query)
        }.flatMapLatest { it.observesSnapshot() }
            .map { it.documents.mapNotNull { doc -> doc.id.toIntOrNull() } }

    private fun favoritesRef(currentUser: FirebaseUser): CollectionReference {
        val database = FirebaseFirestore.getInstance()
        return database.collection("users/${currentUser.uid}/favorites")
    }

    class NotPreparedException : RuntimeException()

    companion object {
        private const val DEBUG: Boolean = false
    }
}
