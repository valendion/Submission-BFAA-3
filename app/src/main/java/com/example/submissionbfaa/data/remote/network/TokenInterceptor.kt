package com.example.submissionbfaa.data.remote.network

import com.example.submissionbfaa.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .header(BuildConfig.API_KEY, BuildConfig.VALUE)
            .build()

        return chain.proceed(newRequest)
    }
}