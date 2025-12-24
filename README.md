This is a new [**React Native**](https://reactnative.dev) project, bootstrapped using [`@react-native-community/cli`](https://github.com/react-native-community/cli).

# Nearby Places Map App 📍

A React Native application that leverages **Android Native Modules** and **MVVM Architecture** to fetch and display nearby places on a Google Map.

## 🏗️ Architecture: MVVM (Model-View-ViewModel)
This project is built with a strict separation of concerns between the React Native UI and Android Native logic:

### 1. Android Native (The Engine)
- **Model**: Data classes representing place entities.
- **Repository**: Handles data fetching logic (GPS Location + Places Data).
- **ViewModel**: Manages business logic and exposes state via LiveData.
- **Native Module**: Bridges the ViewModel data to the React Native layer.

### 2. React Native (The UI)
- **Map View**: Renders the map using `react-native-maps`.
- **Markers**: Dynamically displays places fetched from the Native side.
- **Permissions**: Handles runtime Android location permissions.

## 📂 Project Structure
Below is the organization of the MVVM components within the Android source folder:

```text
root/
├── android/app/src/main/java/com/nearbyplacesapp/
│   ├── bridge/           <-- Native Module & Package registration
│   │   ├── PlacesModule.kt
│   │   └── PlacesPackage.kt
│   ├── location/         <-- GPS & Location logic
│   │   └── LocationProvider.kt
│   ├── model/            <-- Data Classes (The 'M' in MVVM)
│   │   └── Place.kt
│   ├── repository/       <-- Data Fetching logic
│   │   └── PlacesRepository.kt
│   └── viewmodel/        <-- Business Logic (The 'VM' in MVVM)
│       └── PlacesViewModel.kt
├── src/                  <-- React Native Source
│   └── screens/
│       └── MapScreen.tsx <-- UI Layer (The 'V' in MVVM)
└── App.tsx

### You have to use your own MAP_KEY 

---

## 🚀 Features
- **Native Location Fetching**: Uses Android APIs for high-accuracy location.
- **MVVM Design**: Clean, testable, and maintainable native code.
- **Real-time Map Updates**: Centers the map and places markers based on live GPS data.
- **Distance Calculation**: Shows the distance from your current position to each marker.

---

## 🛠️ Getting Started


> **Note**: Make sure you have completed the [Set Up Your Environment](https://reactnative.dev/docs/set-up-your-environment) guide before proceeding.



## Step 1: Start Metro

First, you will need to run **Metro**, the JavaScript build tool for React Native.

To start the Metro dev server, run the following command from the root of your React Native project:

```sh
# Using npm
npm start

# OR using Yarn
yarn start
```

## Step 2: Build and run your app

With Metro running, open a new terminal window/pane from the root of your React Native project, and use one of the following commands to build and run your Android or iOS app:

### Android

```sh
# Using npm
npm run android

# OR using Yarn
yarn android
```

### iOS

For iOS, remember to install CocoaPods dependencies (this only needs to be run on first clone or after updating native deps).

The first time you create a new project, run the Ruby bundler to install CocoaPods itself:

```sh
bundle install
```

Then, and every time you update your native dependencies, run:

```sh
bundle exec pod install
```

For more information, please visit [CocoaPods Getting Started guide](https://guides.cocoapods.org/using/getting-started.html).

```sh
# Using npm
npm run ios

# OR using Yarn
yarn ios
```

If everything is set up correctly, you should see your new app running in the Android Emulator, iOS Simulator, or your connected device.

This is one way to run your app — you can also build it directly from Android Studio or Xcode.

## Step 3: Modify your app

Now that you have successfully run the app, let's make changes!

Open `App.tsx` in your text editor of choice and make some changes. When you save, your app will automatically update and reflect these changes — this is powered by [Fast Refresh](https://reactnative.dev/docs/fast-refresh).

When you want to forcefully reload, for example to reset the state of your app, you can perform a full reload:

- **Android**: Press the <kbd>R</kbd> key twice or select **"Reload"** from the **Dev Menu**, accessed via <kbd>Ctrl</kbd> + <kbd>M</kbd> (Windows/Linux) or <kbd>Cmd ⌘</kbd> + <kbd>M</kbd> (macOS).
- **iOS**: Press <kbd>R</kbd> in iOS Simulator.

## Congratulations! :tada:

You've successfully run and modified your React Native App. :partying_face:

### Now what?

- If you want to add this new React Native code to an existing application, check out the [Integration guide](https://reactnative.dev/docs/integration-with-existing-apps).
- If you're curious to learn more about React Native, check out the [docs](https://reactnative.dev/docs/getting-started).

# Troubleshooting

If you're having issues getting the above steps to work, see the [Troubleshooting](https://reactnative.dev/docs/troubleshooting) page.

# Learn More

To learn more about React Native, take a look at the following resources:

- [React Native Website](https://reactnative.dev) - learn more about React Native.
- [Getting Started](https://reactnative.dev/docs/environment-setup) - an **overview** of React Native and how setup your environment.
- [Learn the Basics](https://reactnative.dev/docs/getting-started) - a **guided tour** of the React Native **basics**.
- [Blog](https://reactnative.dev/blog) - read the latest official React Native **Blog** posts.
- [`@facebook/react-native`](https://github.com/facebook/react-native) - the Open Source; GitHub **repository** for React Native.


