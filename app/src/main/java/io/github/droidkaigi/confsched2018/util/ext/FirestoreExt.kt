package io.github.droidkaigi.confsched2018.util.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import io.github.droidkaigi.confsched2018.util.FirestoreKtx

suspend fun DocumentReference.getsSnapshot() = FirestoreKtx.getDocumentSnapshot(this)
suspend fun DocumentReference.sets(value: Any) = FirestoreKtx.setDocument(this, value)
suspend fun DocumentReference.deletes() = FirestoreKtx.deleteDocument(this)

suspend fun CollectionReference.adds(value: Any) = FirestoreKtx.addDocumentToCollection(this, value)

suspend fun Query.observesSnapshot() = FirestoreKtx.observeQuerySnapshot(this)
suspend fun Query.isEmpty() = FirestoreKtx.isQuerySnapshotEmpty(this)
