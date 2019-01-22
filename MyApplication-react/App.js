import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import {Login} from "./app/views/Login";
import {StackNavigator} from 'react-navigation'
import {Home} from "./app/views/Home";
import {Add} from  "./app/views/Add";

const MyRoutes = StackNavigator({
    LoginRT: {
        screen: Login
    },
    HomeRT: {
        screen: Home
    },
    AddRT: {
        screen: Add
    }
},
    {
        initialRouteName: 'LoginRT'
});
export default class App extends React.Component {
  render() {
      console.disableYellowBox = true;
    return (
      <MyRoutes/>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
