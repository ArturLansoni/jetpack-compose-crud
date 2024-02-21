package com.arturlansoni.servicelist.core.infra.database

import com.arturlansoni.servicelist.core.Failure
import com.arturlansoni.servicelist.domain.entities.Service
import com.google.firebase.firestore.DocumentSnapshot

interface DatabaseClient {
    suspend fun getAll(): Pair<Failure, List<DocumentSnapshot>?>
    suspend fun get(id: String): Pair<Failure, DocumentSnapshot?>
    suspend fun create(service: Service): Pair<Failure, Void?>
    suspend fun update(service: Service): Pair<Failure, Void?>
    suspend fun delete(id: String): Pair<Failure, Void?>
}