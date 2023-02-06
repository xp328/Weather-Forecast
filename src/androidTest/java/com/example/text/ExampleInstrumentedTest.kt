package com.example.text

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.text.network.MyApiResponseEntity
import com.example.text.network.OkHttpUtils
import com.example.text.network.UserWeather
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.text", appContext.packageName)
    }


    @Test
    fun coroutinePost() {
        val uriString = "http://175.178.70.219:8083/api/weather/today"
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                //JSON RequestBody
                val jsonRequestString = "{\n" +
                        "    \"city\" : \"新疆\"\n" +
                        "}"

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = jsonRequestString.toRequestBody(mediaType)
                //FormBody
                val formBody = FormBody.Builder().add("username", "user01").build()

                val response = OkHttpUtils.handle(uriString, OkHttpUtils.POST, requestBody)
                if (response.code == 200) {
                    val responseBodyString = response.body?.string()

                    val responseData: MyApiResponseEntity<UserWeather> =
                        Gson().fromJson(responseBodyString,
                            object : TypeToken<MyApiResponseEntity<UserWeather>>() {}.type)

                    Log.e("TEST", responseData.data.toString())
                }
            }.onFailure {
                Log.e("TEST", "coroutinePost: ", it)
            }

        }

    }
}