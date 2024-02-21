package com.arturlansoni.servicelist.core.infra.auth

import com.arturlansoni.servicelist.core.Failure
import com.google.firebase.auth.FirebaseUser

interface AuthClient {
    fun userIsLoggedIn(): Boolean
    suspend fun createUser(email: String, password: String): Pair<Failure, FirebaseUser?>
    suspend fun signIn(email: String, password: String): Pair<Failure, FirebaseUser?>
    suspend fun signOut()
}