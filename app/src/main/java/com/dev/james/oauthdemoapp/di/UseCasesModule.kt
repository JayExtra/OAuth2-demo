package com.dev.james.oauthdemoapp.di

import com.dev.james.oauthdemoapp.data.remote.AuthApi
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import com.dev.james.oauthdemoapp.domain.SignInUsecase
import com.dev.james.oauthdemoapp.domain.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideAuthRepo(
        api : AuthApi
    ) : AuthRepository =
        AuthRepository(api)

    @Provides
    @Singleton
    fun provideSignUpUseCase(
       repository: AuthRepository
    ) : SignUpUseCase =
        SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInUsecase(
        repository: AuthRepository
    ) : SignInUsecase =
        SignInUsecase(repository)
}