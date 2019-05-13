package com.globant.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelResquestGenerator {
    private val PRIVATE_API_KEY_ARG = "hash"
    private val PUBLIC_API_KEY_ARG = "apikey"
    private val TS = "ts"
    private val TS_VALUE = "1"
    private val MAX_TRYOUTS = 3
    private val INIT_TRYOUT = 1
    private val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
            )
            .addInterceptor { chain ->
                val defaultRequest = chain.request()

                val defaulthttpUrl = defaultRequest.url()

                val httpUrl = defaulthttpUrl.newBuilder()
                        .addQueryParameter(PUBLIC_API_KEY_ARG, "b9baa830257d91dacc32db89d34d1f09")
                        .addQueryParameter(PRIVATE_API_KEY_ARG, "3e207d05ea54389185beb3caa92ffc66")
                        .addQueryParameter(TS, TS_VALUE)
                        .build()

                val requestBuilder = defaultRequest.newBuilder()
                        .url(httpUrl)

                chain.proceed(requestBuilder.build())
    }
            .addInterceptor { chain ->
                var request = chain.request()
                var response = chain.proceed(request)
                var tryOuts = INIT_TRYOUT

                while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                    Log.d(this@MarvelResquestGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}")
                    tryOuts++
                    response = chain.proceed(request)
                }

                response
            }

    private val builder = Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com/public/")
            .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
