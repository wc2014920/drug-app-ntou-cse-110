package com.example.drug_app2021.data.network

//import com.example.drug_app2021.data.response.User
import com.example.drug_app2021.data.response.LoginResponse
import com.example.drug_app2021.data.response.StatusResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Authapi {

    @FormUrlEncoded
    @POST("api/user/mobile/check")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("api/user/mobile/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("cellphone") cellphone: String
    ) : StatusResponse

}