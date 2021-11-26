package com.example.drug_app2021.data.network

import com.example.drug_app2021.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object{
        private const val BASE_URL = "https://drug-app-ntoucse.herokuapp.com/"
    }

    fun<Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ):Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor{ chain ->  
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Authorization", "Bearer $authToken")
                    }.build())
                }
                .also { client ->
                if(BuildConfig.DEBUG){
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}