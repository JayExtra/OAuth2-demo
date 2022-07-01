package com.dev.james.oauthdemoapp.di

import com.dev.james.oauthdemoapp.data.local.room.Dao
import com.dev.james.oauthdemoapp.data.local.room.WillManagerDatabase
import com.dev.james.oauthdemoapp.data.remote.AuthApi
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import com.dev.james.oauthdemoapp.domain.ForgotPasswordUsecase
import com.dev.james.oauthdemoapp.domain.GetCategoriesUsecase
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
        api : AuthApi ,
        dao: Dao,
        db : WillManagerDatabase
    ) : AuthRepository =
        AuthRepository(api ,dao , db)

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

    @Provides
    @Singleton
    fun provideForgotPasswordUsecase(
        repository: AuthRepository
    ) : ForgotPasswordUsecase =
        ForgotPasswordUsecase(repository)

    @Provides
    @Singleton
    fun provideCategoryUsecase(
        repository: AuthRepository
    ) : GetCategoriesUsecase =
        GetCategoriesUsecase(repository)
}