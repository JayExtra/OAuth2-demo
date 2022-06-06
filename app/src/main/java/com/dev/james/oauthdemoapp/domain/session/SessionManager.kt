package com.dev.james.oauthdemoapp.domain.session

import android.util.Log
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.data.local.datastore.DatastoreManager
import com.dev.james.oauthdemoapp.data.local.datastore.PrefKeysManager
import com.dev.james.oauthdemoapp.data.model.LoginResponse
import com.dev.james.oauthdemoapp.data.remote.AuthApi
import com.dev.james.oauthdemoapp.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val dataStoreManager : DatastoreManager ,
    private val repository: AuthRepository,
    private val defaultDispatcher : CoroutineDispatcher = Dispatchers.IO
) {
    //1.keep track of access and refresh token expiry date. We will take the login time and time
    //the token takes to expire and add them , we shall know the expiry date

    //2. when user comes back to app check if the token has expire based on the time the
    //user has come back and the time the token will expire

    //3. if the token has expired make call to get new access token

    //4. replace access token , refresh token and expiry time

    fun saveUserSignInStatus(status : Boolean){
        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.storeUserSignedInValue(PrefKeysManager.IS_USER_SIGNED_IN , status)
        }
    }

    fun readUserSignedInStatus() : Flow<Boolean> {
        return dataStoreManager.readUserSignedInValue(PrefKeysManager.IS_USER_SIGNED_IN)
    }

    fun saveAccessRefreshTokensStartSession(value : LoginResponse){
        CoroutineScope(defaultDispatcher).launch {
            try {
                dataStoreManager.storeAccessToken(PrefKeysManager.ACCESS_TOKEN , value.accessToken)
                dataStoreManager.storeRefreshToken(PrefKeysManager.REFRESH_TOKEN , value.refreshToken)
                startSession(value.accessTokenExpiry)
                saveRefreshTokenExpiryTime(value.refreshTokenExpiry)
            }catch (e : Exception){
                Log.e("LoginViewModel", "saveAccessAndRefreshTokens: error caching value => ${e.localizedMessage}", )
            }
        }


    }

    fun startSession(expiryTime : Int ) {
        val calendar = Calendar.getInstance()
        val userLoginTime = calendar.time
        calendar.time = userLoginTime
        calendar.add(Calendar.SECOND , expiryTime)

        val expiryTime = calendar.time

        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.storeSessionExpiryTime(PrefKeysManager.SESSION_EXPIRY_TIME , expiryTime.time )
        }
    }

    fun saveRefreshTokenExpiryTime(refreshExpiryTime : Int){
        val calendar = Calendar.getInstance()
        val userLoginTime = calendar.time
        calendar.time = userLoginTime
        calendar.add(Calendar.SECOND , refreshExpiryTime)

        val expiryTime = calendar.time

        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.storeSessionExpiryTime(PrefKeysManager.REFRESH_TOKEN_EXPIRY_TIME , expiryTime.time )
        }
    }

    suspend fun refreshTokens(){
        val refreshToken = getRefreshToken()
        refreshToken?.let { token ->
            val tokenResult = repository.refreshTokens(token)

            when(tokenResult){
                is NetworkResource.Success -> {
                    CoroutineScope(defaultDispatcher).launch {
                        dataStoreManager.storeRefreshToken(PrefKeysManager.REFRESH_TOKEN , tokenResult.value.refreshToken)
                        dataStoreManager.storeRefreshToken(PrefKeysManager.ACCESS_TOKEN , tokenResult.value.accessToken)

                        startSession(tokenResult.value.accessTokenExpiry)
                        saveRefreshTokenExpiryTime(tokenResult.value.refreshTokenExpiry)
                    }
                }

                is NetworkResource.Failure -> {
                    val error = tokenResult.errorCode
                    val errorByteStream = tokenResult.errorBody?.byteStream().toString()
                    Log.d("SessionManager", "refreshTokens: $error => $errorByteStream")
                }
                is NetworkResource.Loading -> {
                    Log.d("SessionManager", "refreshTokens: requesting new tokens...")
                }
            }
        }
    }

    fun clearTokens(){
        //will be called when user signs out
        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.clearAccessToken(PrefKeysManager.ACCESS_TOKEN)
            dataStoreManager.clearRefreshToken(PrefKeysManager.REFRESH_TOKEN)
            dataStoreManager.clearSessionExpiryTime(PrefKeysManager.SESSION_EXPIRY_TIME)
            dataStoreManager.clearRefreshTokenExpiryTime(PrefKeysManager.REFRESH_TOKEN_EXPIRY_TIME)
        }
    }

    private fun getRefreshToken(): String? {
        var token : String? = null
        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.readRefreshToken(PrefKeysManager.REFRESH_TOKEN).collect {
                token = it
            }
        }
        return token
    }


    fun isSessionExpired(
        currentTime : Date
    ): Boolean {
        var expTime : Long? = null
        CoroutineScope(defaultDispatcher).launch {
            dataStoreManager.readSessionExpiryTime(PrefKeysManager.SESSION_EXPIRY_TIME).collect{
                expTime = it
            }
        }
        return !currentTime.after(expTime?.let { Date(it) })
    }


}