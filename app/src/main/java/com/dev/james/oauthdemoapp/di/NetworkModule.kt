package com.dev.james.oauthdemoapp.di

import com.dev.james.oauthdemoapp.BuildConfig
import com.dev.james.oauthdemoapp.constants.Consts.BASE_URL
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.remote.AuthApi
import com.dev.james.oauthdemoapp.data.remote.AuthInterceptor
import com.dev.james.oauthdemoapp.data.remote.ErrorInterceptor
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkhttpClient(
       authInterceptor: AuthInterceptor ,
    ) : OkHttpClient {
        val client = OkHttpClient.Builder()
            //A connect timeout defines a time period in which our client should
            // establish a connection with a target host.
            .connectTimeout(30 , TimeUnit.SECONDS)
             //It defines a maximum time of inactivity between
            // two data packets when waiting for the server's response
            .readTimeout(30 , TimeUnit.SECONDS)
             // defines a maximum time of inactivity between two
            // data packets when sending the request to the server.
            .writeTimeout(30 , TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)


        if(BuildConfig.DEBUG) client.addInterceptor(loggingInterceptor)

        return client.build()
    }


    @Provides
    @Singleton
    fun providesAuthInterceptor(
        datastoreManager: DatastoreManager
    ) : Interceptor {
        return AuthInterceptor(datastoreManager)
    }



    @Provides
    @Singleton
    fun provideAuthenticationApi(
        retrofit: Retrofit
    ) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }


    @Singleton
    @Provides
    fun provideAuthApi(
        okhttpClient : OkHttpClient
    ) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()




}