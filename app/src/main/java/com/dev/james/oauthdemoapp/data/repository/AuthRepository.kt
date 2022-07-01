package com.dev.james.oauthdemoapp.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.dev.james.oauthdemoapp.constants.NetworkResource
import com.dev.james.oauthdemoapp.constants.Resource
import com.dev.james.oauthdemoapp.data.local.room.Dao
import com.dev.james.oauthdemoapp.data.local.room.WillManagerDatabase
import com.dev.james.oauthdemoapp.data.mappers.toDomain
import com.dev.james.oauthdemoapp.data.mappers.toEntity
import com.dev.james.oauthdemoapp.data.model.*
import com.dev.james.oauthdemoapp.data.remote.AuthApi
import com.dev.james.oauthdemoapp.domain.models.Categories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val dao: Dao,
    private val db : WillManagerDatabase
) : BaseRepositoryApiCall() {
    suspend fun signUpUser(signUpRequest: SignUpRequest) : NetworkResource<SignUpResponse> = safeApiCall {
        api.signUp(signUpRequest)
    }

    suspend fun signInUser(loginRequest: LoginRequest) : NetworkResource<LoginResponse> = safeApiCall {
        api.signInUser(loginRequest)
    }

    suspend fun refreshTokens(refreshToken : RefreshTokenBody) : NetworkResource<LoginResponse> = safeApiCall {
        api.refreshTokens(refreshToken)
    }

    suspend fun forgotPassword(email : ForgotPasswordBody) : NetworkResource<ForgotPasswordResponse> = safeApiCall {
        api.forgotPassword(email)
    }

    fun getCategoriesList() : Flow<Resource<List<Categories>>> = flow {

        val categoriesList = dao.getCategoriesList().map {
            it.toDomain()
        }
            try {
                val categoryResponse = api.getCategoryDiagrams()
                db.withTransaction {
                    dao.deleteAllCategories()
                    dao.addCategories(categoryResponse.dataDto.categoryDiagramDto.map { it.toDomain().toEntity() })
                }
                val finalData = dao.getCategoriesList()
               emit( Resource.Success(data = finalData.map { it.toDomain() }))
            }catch (error : HttpException){
                val resultErrorCode = error.code()
                val errorBody = error.message()
                Log.d("AuthRepository", "getCategoriesList: $resultErrorCode : ${errorBody.toString()} ")
                emit(Resource.Error(message = "Error $resultErrorCode: Oops! Something went wrong " , data = categoriesList ))
            }catch (e : IOException){
                val error = e.localizedMessage
                Log.d("AuthRepository", "getCategoriesList: error : $error ")
                emit(Resource.Error(message = "Couldn't reach server check your internet connection" , data = categoriesList))
            }

    }
}