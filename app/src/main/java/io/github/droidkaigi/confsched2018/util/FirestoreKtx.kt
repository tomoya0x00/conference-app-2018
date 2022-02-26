package io.github.droidkaigi.confsched2018.util

import androidx.annotation.CheckResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

object FirestoreKtx {

    @CheckResult suspend fun getDocumentSnapshot(ref: DocumentReference): DocumentSnapshot {
        return ref.get().await()
    }

    @CheckResult suspend fun setDocument(ref: DocumentReference, value: Any) {
        ref.set(value).await()
    }

    @CheckResult suspend fun deleteDocument(ref: DocumentReference) {
        ref.delete().await()
    }

    @CheckResult suspend fun addDocumentToCollection(ref: CollectionReference, value: Any) {
        ref.add(value).await()
    }

    @CheckResult fun observeQuerySnapshot(ref: Query): Flow<QuerySnapshot> =
        callbackFlow {
            val listener = ref.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    cancel(CancellationException("snapshot error", error))

                } else {
                    snapshot?.let {
                        trySendBlocking(it)
                    } ?: cancel(CancellationException("snapshot is null"))
                }
            }

            awaitClose { listener.remove() }
        }

    @CheckResult suspend fun getQuerySnapshot(ref: Query): QuerySnapshot {
        return observeQuerySnapshot(ref).first()
    }

    @CheckResult suspend fun isQuerySnapshotEmpty(ref: Query): Boolean {
        return getQuerySnapshot(ref).isEmpty
    }
}
