package com.example.submissionbfaa.data.remote.network


import com.example.submissionbfaa.BuildConfig
import com.example.submissionbfaa.utils.TokenInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiConfig {
    private fun doRequest(): Retrofit {

        val moshi = Moshi.Builder().build()
        val tokenInterseptor = TokenInterceptor()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(tokenInterseptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun apiUser(): ApiServiceUser {
        return doRequest().create(ApiServiceUser::class.java)
    }
}