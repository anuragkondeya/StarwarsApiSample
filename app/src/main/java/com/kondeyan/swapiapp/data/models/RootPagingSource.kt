package com.kondeyan.swapiapp.data.models

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kondeyan.swapiapp.ui.model.ViewData

class RootPagingSource(val data: List<ViewData>) :
    PagingSource<String, ViewData>() {

    override fun getRefreshKey(state: PagingState<String, ViewData>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ViewData> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )

    }
}