package com.kondeyan.swapiapp.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kondeyan.swapiapp.api.SWApi
import com.kondeyan.swapiapp.data.models.PagedDataSource
import com.kondeyan.swapiapp.data.models.Root
import com.kondeyan.swapiapp.data.models.RootPagingSource
import com.kondeyan.swapiapp.ui.model.ViewData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class Domain @Inject constructor(val api: SWApi) {

    private fun <T> getDataObject(jsonObject: JSONObject?, type :Class<T>): T? {
        return try {
            Gson().fromJson(jsonObject.toString(), type)
        } catch (exception: JsonSyntaxException) {
            Log.e("Anurag","Exception "+exception)
            null
        }
    }

    fun getRootDataAsLivedata(data: List<ViewData>): LiveData<PagingData<ViewData>>{
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RootPagingSource(data) }
        ).liveData
    }

    fun <T> getDataStream(url: String, type: Class<T>): LiveData<PagingData<ViewData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagedDataSource(api,url, type) }
        ).liveData
    }

    suspend fun getRootData(): Root? {
        return getDataObject(api.getDataFromServer(), Root::class.java)
    }
    suspend fun <T> getDataFromServer(url: String, type :Class<T>): T? {
        return getDataObject(api.getDataFromServer(url), type)
    }


}