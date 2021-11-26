package com.example.drug_app2021.data.network

//import com.example.drug_app2021.data.response.StatusResponse
//import com.example.drug_app2021.data.response.User
import com.example.drug_app2021.data.response.ListResponse
import okhttp3.ResponseBody
import retrofit2.http.POST

interface UserApi {

    @POST("api/user/mobile/logout")
    suspend fun logout() :ResponseBody

    @POST("api/user/mobile/download")
    suspend fun getAlarmList() : ListResponse



}