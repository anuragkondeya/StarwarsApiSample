package com.kondeyan.swapiapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kondeyan.swapiapp.R
import com.kondeyan.swapiapp.data.models.*
import com.kondeyan.swapiapp.domain.Domain
import com.kondeyan.swapiapp.ui.base.BaseViewModel
import com.kondeyan.swapiapp.ui.model.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class SWViewModel @Inject constructor(application: Application, val domain: Domain) :
    BaseViewModel(application) {

    private var root: Root? = null

    //This will be output of details currently just priting out dataClass.toString
    var details: String? = null

    private var parentTitle: String? = null

    private val rootMap = HashMap<String, String>()

    val FILM = getString(R.string.root_films)
    val PEOPLE = getString(R.string.root_people)
    val PLANET = getString(R.string.root_planets)
    val SPECIES = getString(R.string.root_species)
    val STARTSHIPS = getString(R.string.root_starships)
    val VEHICLES = getString(R.string.root_vehicles)

    private fun mapRootValuesToUrl() {
        root?.apply {
            if (!films.isNullOrEmpty()) rootMap[FILM] = films
            if (!people.isNullOrEmpty()) rootMap[PEOPLE] = people
            if (!planets.isNullOrEmpty()) rootMap[PLANET] = planets
            if (!species.isNullOrEmpty()) rootMap[SPECIES] = species
            if (!starships.isNullOrEmpty()) rootMap[STARTSHIPS] = starships
            if (!vehicles.isNullOrEmpty()) rootMap[VEHICLES] = vehicles
        }
    }

    private fun <T> getPagedData(url: String, type: Class<T>): LiveData<PagingData<ViewData>> {
        return domain.getDataStream(url, type)
            .cachedIn(viewModelScope)
    }

    fun fetchRootData() = viewModelScope.launch {
        val response = domain.getRootData()
        response?.let {
            root = it
            mapRootValuesToUrl()
            notifySuccess()
        } ?: notifyError()
    }

    /**
     * Function to get response on POJO which can be used for presentation
     */
    fun fetchDetailsData(url: String) = viewModelScope.launch {
        notifyLoading()
        parentTitle?.apply {
            if (this == FILM) {
                details = domain.getDataFromServer(url, Film::class.java).toString()
            }
            if (this == PEOPLE) {
                details = domain.getDataFromServer(url, People::class.java).toString()
            }
            if (this == PLANET) {
                details = domain.getDataFromServer(url, Planet::class.java).toString()
            }
            if (this == SPECIES) {
                details = domain.getDataFromServer(url, Species::class.java).toString()
            }
            if (this == STARTSHIPS) {
                details = domain.getDataFromServer(url, StarShip::class.java).toString()
            }
            if (this == VEHICLES) {
                details = domain.getDataFromServer(url, Vehicle::class.java).toString()
            }
            notifySuccess()
        }
    }


    fun getRootData(): LiveData<PagingData<ViewData>> {
        return domain.getRootDataAsLivedata(rootMap.toList().map { ViewData(it.first) })
            .cachedIn(viewModelScope)
    }

    fun loadDetails(title: String): LiveData<PagingData<ViewData>>? {
        notifyLoading()
        this.parentTitle = title
        rootMap[title]?.apply {
            var data: LiveData<PagingData<ViewData>>? = null
            if (title == FILM) {
                data = getPagedData(this, Film::class.java)
            }
            if (title == PEOPLE) {
                data = getPagedData(this, People::class.java)
            }
            if (title == PLANET) {
                data = getPagedData(this, Planet::class.java)
            }
            if (title == SPECIES) {
                data = getPagedData(this, Species::class.java)
            }
            if (title == STARTSHIPS) {
                data = getPagedData(this, StarShip::class.java)
            }
            if (title == VEHICLES) {
                data = getPagedData(this, Vehicle::class.java)
            }
            notifySuccess()
            return data
        } ?: notifyError()
        return null
    }

}