package com.example.drug_app2021.data.repository

import android.util.Log
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Resource<T>{
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiCall.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun logout(api: UserApi) = safeApiCall {
        api.logout()
    }
}
