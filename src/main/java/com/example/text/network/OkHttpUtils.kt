package com.example.text.network

import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.util.concurrent.TimeUnit

object OkHttpUtils {

    const val POST = "POST"
    const val GET = "GET"
    const val PUT = "PUT"

    private var okHttpClient : OkHttpClient? = null

    fun handle(url: String, requestMethod: String, requestBody: RequestBody?): Response {
        if (okHttpClient == null) okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5,TimeUnit.SECONDS)
            .callTimeout(5,TimeUnit.SECONDS).build()

        val request = Request.Builder()
            .method(requestMethod, if (requestMethod == GET) null else requestBody)
            .url(url).build()

        val response = okHttpClient!!.newCall(request).execute()
        return response
    }
}