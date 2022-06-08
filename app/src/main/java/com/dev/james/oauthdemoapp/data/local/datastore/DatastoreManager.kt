package com.dev.james.oauthdemoapp.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.ACCESS_TOKEN
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.DEFAULT_KEY
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.IS_USER_SIGNED_IN
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.REFRESH_TOKEN
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.REFRESH_TOKEN_EXPIRY_TIME
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.SESSION_EXPIRY_TIME
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager.STORE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    @ApplicationContext private val context : Context
) {
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
            name = STORE_NAME
        )
    }

    //store our refresh token
    suspend fun storeRefreshToken(key : Preferences.Key<String> , value : String) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    //store our access token
    suspend fun storeAccessToken(key : Preferences.Key<String> , value : String) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    //store session expiry time
    suspend fun storeSessionExpiryTime(key : Preferences.Key<Long> , value : Long) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    //store refresh token expiry time
    suspend fun storeRefreshTokenExpiryTime(key : Preferences.Key<Long> , value : Long) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    //store user signed in value
    suspend fun storeUserSignedInValue(key : Preferences.Key<Boolean> , value : Boolean) {
        context.dataStore.edit {
            it[key] = value
        }
    }


    // read refresh token
    fun readRefreshToken(key : Preferences.Key<String>) : Flow<String> {
        return context.dataStore.data.map {
            it[key]?: DEFAULT_KEY
        }.catch { exception ->
            if(exception is IOException){
                Log.e(
                    "DatastoreManager",
                    "readRefreshToken: read refresh token : error reading exception",
                    exception
                )
                emit(DEFAULT_KEY)
            }
        }
    }

    // read access token
    fun readAccessToken(key : Preferences.Key<String>) : Flow<String> {
        return context.dataStore.data.map {
            it[key]?: DEFAULT_KEY
        }.catch { exception ->
            if(exception is IOException){
                Log.e(
                    "DatastoreManager",
                    "readAccessToken: read refresh token : errpr reading exception",
                    exception
                )
                emit(DEFAULT_KEY)
            }
        }
    }

    // read session expiry time
    fun readSessionExpiryTime(key : Preferences.Key<Long>) : Flow<Long> {
        return context.dataStore.data.map {
            it[key]?: 0L
        }.catch { exception ->
            if(exception is IOException){
                Log.e(
                    "DatastoreManager",
                    "session expiry time : error reading exception",
                    exception
                )
                emit(0L)
            }
        }
    }

    // read refresh token expiry time
    fun readRefreshTokenExpiryTime(key : Preferences.Key<Long>) : Flow<Long> {
        return context.dataStore.data.map {
            it[key]?: 0L
        }.catch { exception ->
            if(exception is IOException){
                Log.e(
                    "DatastoreManager",
                    "refresh token expiry time : error reading exception",
                    exception
                )
                emit(0L)
            }
        }
    }

    // read user signed in value
    fun readUserSignedInValue(key : Preferences.Key<Boolean>) : Flow<Boolean> {
        return context.dataStore.data.map {
            it[key]?: false
        }.catch { exception ->
            if(exception is IOException){
                Log.e(
                    "DatastoreManager",
                    "is user signed in : error reading exception",
                    exception
                )
                emit(false)
            }
        }
    }

    suspend fun clearAccessToken(key : Preferences.Key<String>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }
    suspend fun clearRefreshToken(key : Preferences.Key<String>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }
    suspend fun clearSessionExpiryTime(key : Preferences.Key<Long>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }
    suspend fun clearRefreshTokenExpiryTime(key : Preferences.Key<Long>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }
    
    val authSessionPreferenceFlow = context.dataStore.data.catch {  exception -> 
        if(exception is IOException) {
            Log.e("DatastoreManager", "Error:error fetching preferences ", exception )
            emit(emptyPreferences())
        }else {
            throw exception
        }
    }.map { preferences ->
        val accessToken = preferences[ACCESS_TOKEN] ?: "no token"
        val refreshToken = preferences[REFRESH_TOKEN] ?: "no token"
        val sessionExpiry = preferences[SESSION_EXPIRY_TIME] ?: 0L
        val refreshTokenExpiry = preferences[REFRESH_TOKEN_EXPIRY_TIME] ?: 0L
        val signInStatus = preferences[IS_USER_SIGNED_IN] ?: false

        AllSessionPreferences(
            accessToken ,
            refreshToken ,
            sessionExpiry ,
            refreshTokenExpiry ,
            signInStatus
        )
    }
}


