package com.globant.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val PRIVATE_API_KEY_ARG = "hash"
private const val PRIVATE_API_KEY_ARG_VALUE = "b9baa830257d91dacc32db89d34d1f09"
private const val PUBLIC_API_KEY_ARG = "apikey"
private const val PUBLIC_API_KEY_ARG_VALUE = "3e207d05ea54389185beb3caa92ffc66"
private const val MARVEL_BASE_URL = "http://gateway.marvel.com/public/"
private const val TS = "ts"
private const val TS_VALUE = "1"
private const val MAX_TRYOUTS = 3
private const val INIT_TRYOUT = 1

class MarvelRequestGenerator {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor { chain ->
            val defaultRequest = chain.request()

            val defaultHttpUrl = defaultRequest.url()

            val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(PUBLIC_API_KEY_ARG, PRIVATE_API_KEY_ARG_VALUE)
                .addQueryParameter(PRIVATE_API_KEY_ARG, PUBLIC_API_KEY_ARG_VALUE)
                .addQueryParameter(TS, TS_VALUE)
                .build()

            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = INIT_TRYOUT

            while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                Log.d(
                    this@MarvelRequestGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl(MARVEL_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
