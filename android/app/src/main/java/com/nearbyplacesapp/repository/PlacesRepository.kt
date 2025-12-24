package com.nearbyplacesapp.repository

import com.nearbyplacesapp.location.LocationProvider
import com.nearbyplacesapp.model.Place
import android.location.Location
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class PlacesRepository(private val locationProvider: LocationProvider) {

    private val client = OkHttpClient()
    private val apiKey = "AIzaSyB_zjSQ_Pa5KN-iQXWjnHusWxrCLC_Ht9c" // Same as in Manifest

    fun fetchNearbyPlaces(
        onSuccess: (List<Place>) -> Unit,
        onError: (String) -> Unit
    ) {
        locationProvider.getLocation({ location ->

            // Construct the Google Places URL
            // type=restaurant,cafe,hospital etc. radius=1500 meters
            val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=${location.latitude},${location.longitude}" +
                    "&radius=1500" +
                    "&key=$apiKey"

            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onError(e.message ?: "Network error")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    if (body != null) {
                        val json = JSONObject(body)
                        println("DEBUG_PLACES: $body") // Look for this in your Logcat/Terminal

                            if (body != null) {
                                val json = JSONObject(body)

                                // Check if Google returned an error status
                                if (json.getString("status") != "OK") {
                                    val errorMessage = json.optString(
                                        "error_message",
                                        "Google API Error: ${json.getString("status")}"
                                    )
                                    onError(errorMessage)
                                    return
                                }
                            }
                        val results = json.getJSONArray("results")
                        val placesList = mutableListOf<Place>()

                        for (i in 0 until results.length()) {
                            val item = results.getJSONObject(i)
                            val loc = item.getJSONObject("geometry").getJSONObject("location")

                            placesList.add(Place(
                                name = item.getString("name"),
                                latitude = loc.getDouble("lat"),
                                longitude = loc.getDouble("lng"),
                                distance = 0.0 // You can calculate actual distance here if needed
                            ))
                        }
                        onSuccess(placesList)
                    } else {
                        onError("Empty response from Google")
                    }
                }
            })

        }, { errorMessage ->
            onError(errorMessage)
        })
    }
}