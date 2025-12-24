package com.nearbyplacesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nearbyplacesapp.location.LocationProvider
import com.nearbyplacesapp.model.Place
import com.nearbyplacesapp.repository.PlacesRepository

class PlacesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlacesRepository(LocationProvider(application))

    val places: MutableLiveData<List<Place>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun loadPlaces() {
        repository.fetchNearbyPlaces(
            { places.postValue(it) },
            { error.postValue(it) }
        )
    }
}
