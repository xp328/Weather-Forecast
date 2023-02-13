package com.example.text.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

object OkHttpUtils {

    const val BASE_URL = "43.139.251.199:8083"
    const val POST = "POST"
    const val GET = "GET"
    val gson = Gson()

    private var okHttpClient : OkHttpClient? = null

    fun handle(url: String, requestMethod: String, requestBody: RequestBody?): Response {
        if (okHttpClient == null) okHttpClient = OkHttpClient.Builder().build()

        //创建一个请求对象
        val request = Request.Builder()
            .method(requestMethod, if (requestMethod == GET) null else requestBody)
            .url(url).build()

        //发送请求，获取响应数据
        val response = okHttpClient!!.newCall(request).execute()
        return response
    }
}