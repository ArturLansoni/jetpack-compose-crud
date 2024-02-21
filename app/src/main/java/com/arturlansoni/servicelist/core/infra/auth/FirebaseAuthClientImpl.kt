package com.arturlansoni.servicelist.core.infra.auth

import android.util.Log
import com.arturlansoni.servicelist.core.Failure
import com.arturlansoni.servicelist.utils.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class FirebaseAuthClientImpl(
    private val client: FirebaseAuth
) : AuthClient {

    override fun userIsLoggedIn(): Boolean {
        val currentUser = client.currentUser
        return currentUser != null
    }

    override suspend fun createUser(email: String, password: String): Pair<Failure, FirebaseUser?> {
        return try {
            client.createUserWithEmailAndPassword(email, password).await()
            Pair(Failure(null), client.currentUser)
        } catch (e: Exception) {
            Log.e(TAG, "createUser - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun signIn(email: String, password: String): Pair<Failure, FirebaseUser?> {
        return try {
            client.signInWithEmailAndPassword(email, password).await()
            Pair(Failure(null), client.currentUser)
        } catch (e: Exception) {
            Log.e(TAG, "signIn - exception: ${e}, ${e.cause}")
            Pair(Failure(e.message), null)
        }
    }

    override suspend fun signOut() {
        client.signOut()
    }
}
