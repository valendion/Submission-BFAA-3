package com.example.submissionbfaa.data.remote.network


import com.example.submissionbfaa.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    single { TokenInterceptor() }
    single { Moshi.Builder().build() }
//    single { GsonBuilder().setLenient().create()}
    factory { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    factory { provideOkHttp(get(), get()) }
    single { provideRetrofit(get(), get()) }
    factory { provideService(get()) }
}

private fun provideOkHttp(
    tokenInterceptor: TokenInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}


private fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

fun provideService(retrofit: Retrofit): ApiServiceUser {
    return retrofit.create(ApiServiceUser::class.java)
}


