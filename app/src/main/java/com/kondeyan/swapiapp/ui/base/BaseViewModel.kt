package com.kondeyan.swapiapp.ui.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

enum class State {
    LOADING,
    ERROR,
    SUCCESS,
}

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val networkStateLivedata: MutableLiveData<State> by lazy {
        MutableLiveData<State>()
    }

    fun getString(@StringRes id: Int): String {
        return getApplication<Application>().resources.getString(id)
    }

    fun notifyLoading() = networkStateLivedata.postValue(State.LOADING)
    fun notifySuccess() = networkStateLivedata.postValue(State.SUCCESS)
    fun notifyError() = networkStateLivedata.postValue(State.ERROR)

}
