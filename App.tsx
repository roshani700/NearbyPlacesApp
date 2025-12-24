import React from 'react';
import { StyleSheet } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import MapScreen from './src/MapScreen';

const App: React.FC = () => {
  return (
    <SafeAreaView style={styles.container}>
      <MapScreen />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
