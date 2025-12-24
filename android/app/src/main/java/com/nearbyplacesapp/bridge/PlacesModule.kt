package com.nearbyplacesapp.bridge

import android.app.Application
import androidx.lifecycle.Observer
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.UiThreadUtil
import com.nearbyplacesapp.model.Place
import com.nearbyplacesapp.viewmodel.PlacesViewModel

class PlacesModule(
    reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

    private val viewModel =
        PlacesViewModel(reactContext.applicationContext as Application)

    override fun getName(): String = "PlacesModule"

    @ReactMethod
    fun getNearbyPlaces(promise: Promise) {
        // 1. Ensure we are on the Main Thread (ViewModel/LiveData requirement)
        UiThreadUtil.runOnUiThread {
            try {
                // 2. Use a one-time observer or access the current value directly
                // If loadPlaces() is asynchronous, we wait for the first valid value
                viewModel.places.observeForever(object : Observer<List<Place>> {
                    override fun onChanged(value: List<Place>) {
                        val array = Arguments.createArray()
                        value.forEach { place ->
                            val map = Arguments.createMap()
                            map.putString("name", place.name)
                            map.putDouble("latitude", place.latitude)
                            map.putDouble("longitude", place.longitude)
                            map.putDouble("distance", place.distance)
                            array.pushMap(map)
                        }

                        // 3. Resolve and IMMEDIATELY remove the observer
                        promise.resolve(array)
                        viewModel.places.removeObserver(this)
                    }
                })

                // 4. Handle errors once
                viewModel.error.observeForever(object : Observer<String> {
                    override fun onChanged(value: String) {
                        promise.reject("ERROR", value)
                        viewModel.error.removeObserver(this)
                    }
                })

                // 5. Trigger the data load
                viewModel.loadPlaces()

            } catch (e: Exception) {
                promise.reject("EXCEPTION", e.message)
            }
        }
    }
}
