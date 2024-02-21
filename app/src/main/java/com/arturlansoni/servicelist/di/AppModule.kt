package com.arturlansoni.servicelist.di

import android.content.Context
import com.arturlansoni.servicelist.core.infra.auth.AuthClient
import com.arturlansoni.servicelist.core.infra.auth.FirebaseAuthClientImpl
import com.arturlansoni.servicelist.core.infra.database.DatabaseClient
import com.arturlansoni.servicelist.core.infra.database.FirebaseDatabaseClientImpl
import com.arturlansoni.servicelist.presentation.BaseApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideAuthClient(): AuthClient {
        return FirebaseAuthClientImpl(Firebase.auth)
    }

    @Singleton
    @Provides
    fun provideDatabaseClient(): DatabaseClient {
        return FirebaseDatabaseClientImpl(Firebase.firestore)
    }
}