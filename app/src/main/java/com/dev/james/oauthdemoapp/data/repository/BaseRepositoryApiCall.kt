package com.dev.james.oauthdemoapp.data.repository

import android.util.Log
import com.dev.james.oauthdemoapp.constants.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class BaseRepositoryApiCall {
    suspend fun <T> safeApiCall(
        apiCall : suspend () -> T
     ) : NetworkResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResource.Success(apiCall.invoke())
            } catch (exception: Throwable) {
                Log.d("ExceptionHere", exception.toString())
                when (exception) {
                    is HttpException -> {
                        NetworkResource.Failure(
                            exception.code(),
                            exception.response()?.errorBody()
                        )
                    }
                    else -> {
                        NetworkResource.Failure(null, null)
                    }
                }
            }
        }
    }

}

