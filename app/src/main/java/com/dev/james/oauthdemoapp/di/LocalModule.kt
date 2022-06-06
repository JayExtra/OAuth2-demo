package com.dev.james.oauthdemoapp.di

import android.content.Context
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    //provide datastore manager

    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context : Context
    ) : DatastoreManager =
        DatastoreManager(context)

    @Provides
    @Singleton
    fun provideSessionManager(
        datastoreManager: DatastoreManager ,
        repository: AuthRepository
    ) : SessionManager =
        SessionManager(datastoreManager , repository)


}