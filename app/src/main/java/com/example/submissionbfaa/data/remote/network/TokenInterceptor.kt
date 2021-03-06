package com.example.submissionbfaa.data.remote.network

import com.example.submissionbfaa.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "token ${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(newRequest)
    }
}