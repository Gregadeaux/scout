package com.team930.allianceselectionapp.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
//            .addQueryParameter(
//                "api_key",
//                "04a03ff73803441c785b1ae76dbdab9c"    //TODO Use your api key this one invalid
//            )
            .build()
        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}