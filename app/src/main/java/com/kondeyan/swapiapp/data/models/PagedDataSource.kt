package com.kondeyan.swapiapp.data.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kondeyan.swapiapp.api.SWApi
import com.kondeyan.swapiapp.ui.model.ViewData
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class PagedDataSource <T> @Inject constructor(val swapi: SWApi, val url: String, val type: Class<T>) :
    PagingSource<String, ViewData>() {

    override fun getRefreshKey(state: PagingState<String, ViewData>): String? {
        TODO("Not yet implemented")
    }
    //Doing this so as to get flexibility of populating the view data
    private fun getViewData(jsonObject: JSONObject) : ViewData{
        val pojo = jsonObject.toPojo(type)
        return when(pojo){
            is Film -> ViewData(title = pojo.title?: "UNKNOWN", url = pojo.url)
            is People -> ViewData(title = pojo.name?: "UNKNOWN", url = pojo.url)
            is Planet -> ViewData(title = pojo.name?: "UNKNOWN", url = pojo.url)
            is Species -> ViewData(title = pojo.name?: "UNKNOWN", url = pojo.url)
            is StarShip -> ViewData(title = pojo.name?: "UNKNOWN", url = pojo.url)
            is Vehicle -> ViewData(title = pojo.name?: "UNKNOWN", url = pojo.url)
            else -> ViewData(title = "UNKNOWN")
        }
    }

    private fun getListOfViewData(response: JSONObject): List<ViewData> {
        val listOfViewData = ArrayList<ViewData>()
        response.let {
            if (it.has("results")) {
                try {
                    val value = it.getJSONArray("results")
                    for (i in 0..value.length()) {
                        listOfViewData.add(getViewData(value.getJSONObject(i)))
                    }
                } catch (exception: JSONException) {
                    Log.e("Anurag","JsonException "+exception)
                }
            } else {
                listOfViewData.add(getViewData(it))
            }
        }
        return listOfViewData
    }

    private fun getNextUrl(response: JSONObject): String? {
        response.let {
            if (it.has("next")) {
                return try {
                    it.getString("next")
                } catch (exception: JSONException) {
                    null
                }
            }
        }
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ViewData> {
        val pageUrl = params.key ?: url
        return try {
            val response = swapi.getDataFromServer(pageUrl)
            var listOfViewData = listOf<ViewData>()
            var nextKey: String? = null
            response?.let {
                listOfViewData = getListOfViewData(it)
                nextKey = getNextUrl(response)
            }
            LoadResult.Page(
                data = listOfViewData,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}

fun <T> JSONObject.toPojo(ofType: Class<T>) : T? {
    return this.let {
        try {
            Gson().fromJson(it.toString(), ofType)
        } catch (exception: JsonSyntaxException) {
            null
        }
    }
}