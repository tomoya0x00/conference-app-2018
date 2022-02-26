package io.github.droidkaigi.confsched2018.util

import androidx.annotation.CheckResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import timber.log.Timber

object FirebaseAuthKtx {
    @CheckResult suspend fun getCurrentUser(): FirebaseUser {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            if (DEBUG) Timber.d("Firestore:Get cached user")
            return currentUser
        }
        val result = runCatching {
            auth.signInAnonymously().await()
        }.onSuccess {
            if (DEBUG) Timber.d("Firestore:Sign in Anonymously")
        }.onFailure {
            if (DEBUG) Timber.d("Firestore:Sign in error")
        }

        return result.getOrThrow().user ?: error("user is null")
    }

    private const val DEBUG: Boolean = false
}
