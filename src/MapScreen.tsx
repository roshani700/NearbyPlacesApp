import React, { useEffect, useState } from 'react';
import { 
  NativeModules, 
  Alert, 
  StyleSheet, 
  View, 
  PermissionsAndroid, 
  Platform 
} from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';

interface Place {
  name: string;
  latitude: number;
  longitude: number;
  distance: number;
}

interface PlacesModuleType {
  getNearbyPlaces: () => Promise<Place[]>;
}

// Access the module safely
const PlacesModule = NativeModules.PlacesModule as PlacesModuleType;

const MapScreen: React.FC = () => {
  const [places, setPlaces] = useState<Place[]>([]);
  // Use 'region' instead of 'initialRegion' if you want the map to move programmatically
  const [region, setRegion] = useState({
    latitude: 37.78825,
    longitude: -122.4324,
    latitudeDelta: 0.05,
    longitudeDelta: 0.05,
  });

  const fetchPlaces = async () => {
    try {
      // 1. Check if Module is linked
      if (!PlacesModule) {
        Alert.alert("Error", "Native Module 'PlacesModule' is not registered.");
        return;
      }

      // 2. Request Android Location Permission FIRST
      if (Platform.OS === 'android') {
        const granted = await PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          {
            title: "Location Permission",
            message: "This app needs access to your location to find nearby places.",
            buttonPositive: "OK"
          }
        );

        if (granted !== PermissionsAndroid.RESULTS.GRANTED) {
          Alert.alert("Permission Denied", "Location access is required to show nearby places.");
          return;
        }
      }

      // 3. Call Native Module ONLY after permission is granted
      const data = await PlacesModule.getNearbyPlaces();
      
      if (data && data.length > 0) {
        setPlaces(data);
        
        // 4. Update the region to focus on the first place found
        setRegion({
          latitude: data[0].latitude,
          longitude: data[0].longitude,
          latitudeDelta: 0.01, // Zoom in closer once data is found
          longitudeDelta: 0.01,
        });
      } else {
        Alert.alert("No Places", "No nearby places were found at your location.");
      }

    } catch (err: any) {
      Alert.alert('Error', err.message || "Failed to fetch places");
    }
  };

  useEffect(() => {
    fetchPlaces();
  }, []);

  return (
    <View style={styles.container}>
      <MapView
        provider={PROVIDER_GOOGLE}
        style={styles.map}
        showsUserLocation={true} // Adds the blue dot for your current position
        // Change initialRegion to region so the map moves when setRegion is called
        region={region} 
      >
        {places.map((p, i) => (
          <Marker
            key={`${p.name}-${i}`}
            coordinate={{
              latitude: p.latitude,
              longitude: p.longitude,
            }}
            title={p.name}
            description={`Distance: ${p.distance.toFixed(2)} m`}
          />
        ))}
      </MapView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1, // Using flex: 1 is more standard for full screen
    backgroundColor: '#fff',
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});

export default MapScreen;