package com.kondeyan.swapiapp.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class SWApi  @Inject constructor(@ApplicationContext val context: Context){

    val networkService by lazy {
        Volley.newRequestQueue(context)
    }

    suspend inline fun performNetworkCall(url: String) = withContext(
        Dispatchers.IO) {
        val future: RequestFuture<JSONObject> = RequestFuture.newFuture<JSONObject>()
        val request = JsonObjectRequest(Request.Method.GET, url.toHttpsUrl(), JSONObject(), future, future)
        networkService.add(request)
        val response = try {
            future.get(10, TimeUnit.SECONDS)
        } catch (exception: Exception) {
            //Null is interpreted in ui layer as error state
            null
        }
        response
    }

    suspend inline fun getDataFromServer(url: String = ROOT_URL): JSONObject? {
        return performNetworkCall(url)
    }

    companion object {
        val ROOT_URL = "https://swapi.dev/api/"
    }
}

fun String.toHttpsUrl() = this.replace("http://","https://")

