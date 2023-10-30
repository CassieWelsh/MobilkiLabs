// App.tsx
import React, { Component, useState } from 'react';
import { View, Text, Button, FlatList, StyleSheet, TouchableHighlight } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

const Tab = createBottomTabNavigator();

// function Screen1({ navigation }) {
//   return (
//     <View>
//       <Text>Экран 1</Text>
//       <Button title="Перейти к Экрану 2" onPress={() => navigation.navigate('Screen2')} />
//     </View>
//   );
// }

// function Screen2({ navigation }) {
//   return (
//     <View>
//       <Text>Экран 2</Text>
//       <Button title="Перейти к Экрану 3" onPress={() => navigation.navigate('Screen3')} />
//     </View>
//   );
// }

function Screen3({ navigation }) {
  return (
    <View>
      <Text>Экран 3</Text>
      <Button title="Перейти к Экрану 1" onPress={() => navigation.navigate('Screen1')} />
    </View>
  );
}

class Screen2 extends Component {
  constructor(props) {
    super(props);
    this.state = { pressing: false };
  }

  _onPressIn = () => {
    this.setState({ pressing: true });
  };

  _onPressOut = () => {
    this.setState({ pressing: false });
  };

  render() {
    return (
      <View style={styles.container}>
        <TouchableHighlight
          onPressIn={this._onPressIn}
          onPressOut={this._onPressOut}
          style={styles.touchable}
        >
          <View style={styles.button}>
            <Text style={styles.welcome}>
              {this.state.pressing ? "НАЖАТО!" : "НАЖМИ!"}
            </Text>
          </View>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF",
  },
  welcome: { fontSize: 20, textAlign: "center", margin: 10, color: "#FFFFFF" },
  touchable: { borderRadius: 100 },
  button: {
    backgroundColor: "#FF00FF",
    borderRadius: 100,
    height: 200,
    width: 200,
    justifyContent: "center",
  },
});

export default function Screen1() {
  const data = [
    { key: 'a' },
    { key: 'b' },
    { key: 'c' },
    { key: 'd' },
    { key: 'a longer example' },
    { key: 'e' },
    { key: 'f' },
    { key: 'g' },
    { key: 'h' },
    { key: 'i' },
    { key: 'j' },
    { key: 'k' },
    { key: 'l' },
    { key: 'm' },
    { key: 'n' },
    { key: 'o' },
    { key: 'p' },
  ];

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#F5FCFF',
    },
    row: {
      fontSize: 24,
      padding: 42,
      borderWidth: 1,
      borderColor: '#DDDDDD',
    },
  });

  const renderItem = ({ item }) => (
    <Text style={styles.row}>{item.key}</Text>
  );

  return (
    <View style={styles.container}>
      <FlatList data={data} renderItem={renderItem} />
    </View>
  );
}


export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Screen1" component={Screen1} options={{ title: 'Экран 1' }} />
        <Tab.Screen name="Screen2" component={Screen2} options={{ title: 'Экран 2' }} />
        {/* <Tab.Screen name="Screen3" component={Screen3} options={{ title: 'Экран 3' }} /> */}
      </Tab.Navigator>
    </NavigationContainer>
  );
}
