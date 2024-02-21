package com.arturlansoni.servicelist.core.infra.database


import android.util.Log
import com.arturlansoni.servicelist.core.Failure
import com.arturlansoni.servicelist.domain.entities.Service
import com.arturlansoni.servicelist.utils.TAG
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

const val COLLECTION = "services"

@Singleton
class FirebaseDatabaseClientImpl(
    private val database: FirebaseFirestore
) : DatabaseClient {

    override suspend fun getAll(): Pair<Failure, List<DocumentSnapshot>?> {
        return try {
            val result = database.collection(COLLECTION).get().await()
            Pair(Failure(null), result.documents.toList())
        } catch (e: Exception) {
            Log.e(TAG, "getAll - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun get(id: String): Pair<Failure, DocumentSnapshot?> {
        return try {
            val result = database.collection(COLLECTION).document(id).get().await()
            Pair(Failure(null), result)
        } catch (e: Exception) {
            Log.e(TAG, "get - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun create(service: Service): Pair<Failure, Void?> {
        return try {
            database.collection(COLLECTION).add(
                hashMapOf(
                    "name" to service.name,
                    "category" to service.category,
                    "rating" to service.rating,
                    "imageURL" to service.imageURL,
                    "location" to service.location,
                )
            ).await()
            Pair(Failure(null), null)
        } catch (e: Exception) {
            Log.e(TAG, "create - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun update(service: Service): Pair<Failure, Void?> {
        return try {
            database.collection(COLLECTION).document(service.id).set(service).await()
            Pair(Failure(null), null)
        } catch (e: Exception) {
            Log.e(TAG, "update - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun delete(id: String): Pair<Failure, Void?> {
        return try {
            database.collection(COLLECTION).document(id).delete().await()
            Pair(Failure(null), null)
        } catch (e: Exception) {
            Log.e(TAG, "delete - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }
}

