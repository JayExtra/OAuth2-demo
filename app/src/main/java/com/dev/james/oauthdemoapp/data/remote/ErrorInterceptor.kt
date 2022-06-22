package com.dev.james.oauthdemoapp.data.remote

import android.content.Context
import android.util.Log
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager
import com.dev.james.oauthdemoapp.data.model.RefreshTokenBody
import com.dev.james.oauthdemoapp.domain.session.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val datastoreManager: DatastoreManager ,
    private val api : AuthApi
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        val refreshToken = runBlocking { datastoreManager.readRefreshTokenOnce(PrefKeysManager.REFRESH_TOKEN) }

        // if 401 : Unauthorized error is thrown
        if (response.code == 401) {
            CoroutineScope(Dispatchers.IO).launch {
                val refreshTokenBody = refreshToken?.let {
                    RefreshTokenBody(refreshToken = it)
                }
                if (refreshTokenBody != null) {
                    try {
                        val result =  api.refreshTokens(refreshTokenBody)
                        datastoreManager.storeRefreshToken( PrefKeysManager.REFRESH_TOKEN , result.refreshToken)
                        datastoreManager.storeAccessToken( PrefKeysManager.ACCESS_TOKEN , result.accessToken)

                    }catch (e : HttpException){
                        Log.d("ErrorInterceptor", "intercept http exception: ${e.localizedMessage?.toString()}")
                    }catch (e : IOException){
                        Log.d("ErrorInterceptor", "intercept io exception: ${e.localizedMessage?.toString()} ")
                    }
                }
            }
        }

        return response

    }
}