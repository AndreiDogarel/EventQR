package com.example.eventqr.di

import com.example.eventqr.data.remote.EventIdProvider
import com.example.eventqr.data.remote.FirebaseAuthManager
import com.example.eventqr.data.remote.FirestoreClientRepository
import com.example.eventqr.domain.repo.ClientRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthManager(auth: FirebaseAuth): FirebaseAuthManager = FirebaseAuthManager(auth)

    @Provides
    @Singleton
    fun provideEventIdProvider(): EventIdProvider = EventIdProvider()

    @Provides
    @Singleton
    fun provideClientRepository(
        firestore: FirebaseFirestore,
        authManager: FirebaseAuthManager,
        eventIdProvider: EventIdProvider
    ): ClientRepository = FirestoreClientRepository(firestore, authManager, eventIdProvider)
}
