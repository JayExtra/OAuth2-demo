package com.dev.james.oauthdemoapp.data.remote

import android.content.Context
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val datastoreManager: DatastoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val request = chain.request()
        val accessToken = runBlocking { datastoreManager.readAccessTokenOnce(PrefKeysManager.ACCESS_TOKEN) }
        val requestBuilder = accessToken?.let {
            request.newBuilder()
                .addHeader("Authorization" , "Bearer $it")
        } ?: request.newBuilder().addHeader("Authorization" , "default token")

        return chain.proceed(requestBuilder.build())
    }
}