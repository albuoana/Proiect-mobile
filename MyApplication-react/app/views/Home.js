import React from 'react';
import {Alert, FlatList, Button, AsyncStorage, StyleSheet, Text, View, NetInfo} from 'react-native';
import {CheckBox, Divider} from 'react-native-elements'
import { Ionicons, FontAwesome } from '@expo/vector-icons';
import Icon from 'react-native-vector-icons/FontAwesome';

import PieChart from 'react-native-pie-chart';
const URL = "http://192.168.43.185:8080/api/v1/cartoons";
const SAVE_ALL = "http://192.168.43.185:8080/api/v1/cartoons/saveAll";
export class Home extends React.Component
{
    constructor(props) {
        super(props);

        this.state = {
            token: null,
            data: [],
            checkedList: []
        };
    }

    getToken = async ()=> {
        try {
            console.log("Intra in listener");
            let token = await AsyncStorage.getItem("auth-token");
            this.setState({token: token});
            this.handleConnect();
        }catch (e) {
            console.log(e);
        }
    };
    handleConnect= ()=> {
        console.log("Enter in handle request");
        this.getAsyncData().catch((error)=> {Alert.alert("Failed loading data", error)});
        NetInfo.isConnected.fetch().then(isConnected => {
            if (isConnected) {
                fetch(SAVE_ALL, {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': this.state.token,
                    },
                    body: JSON.stringify(this.state.data)
                }).then(response => {
                    console.log(response);
                    console.log(JSON.stringify(this.state.data));
                    if (response.status === 200) {
                        Alert.alert("Synced", "Synced success");
                    }
                    else
                        Alert.alert("Error", "Error syncing");
                }).catch((error)=> {
                    Alert.alert("Error", error);
                })
            }
        })};

    setData = async(key, data) => {
        try {
            await AsyncStorage.setItem(key, data);
        }
        catch (e) {
            console.log(e);
        }
    };
    getStorage  = async () => {
        try {
            const value = await AsyncStorage.getItem('auth-token');
            this.setState({token: value});
            this.getRemoteData(value);
        }
        catch (e) {
            console.log(e);
        }
    };
    getAsyncData = async ()=> {
        try {
            const data = await AsyncStorage.getItem('data');
            console.log("Enter getAsyncData");
            console.log(data);
            this.setState({data: JSON.parse(data)});
        } catch (e) {
            console.log(e);
        }
    };
    getRemoteData = (token)=> {
        fetch(URL, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': token,
                }
            }).then((response) => {
                return response.json();
        }).then((responseBody)=> {
            console.log(responseBody);
            this.state.data = [];
            responseBody.forEach(value => {
                    if (!(this.state.data.includes(responseBody)))
                        this.state.data.push(value);
                });
            let data = [];
            this.state.data.forEach(value => {
                data.push(value);
            });
            this.setData('data', JSON.stringify(data)).catch(error => {
                console.log("Error storing data " + error);
            });
            this.changeLocalData(JSON.stringify(data)).catch(error=>{
                console.log("Error", error);
            });
        }).catch((error) => {
            console.log(error);
        })
    };

    //se apeleaza inainte de render()
    componentDidMount() {
        console.log("Enter componentDidMount");

        //aici se face sincronizarea pe server----se salveaza acolo tot ce exista local
        NetInfo.isConnected.addEventListener(
            'connectionChange', this.getToken
        );

        NetInfo.isConnected.fetch().then(isConnected => {
            this.changeLocalData("").catch(error=>{
                console.log("Error", error);
            });
            this.getAsyncData().catch(error=> {
                console.log("Failed to get data", error);
                Alert.alert("Error", "Error while loading offline data");
            });
            if (isConnected) {
                this.getStorage().catch(error => {
                    console.log("Failed to get data", error);
                    Alert.alert("Error", "Error while loading offline data");
                });
            }
        });

        this.props.navigation.addListener('didFocus', ()=>{
            this.getAsyncData().catch(error=>{
                console.log("error on update" + error);
            });
        });

    }

    render()
    {
        const sliceColor = ['#F44336','#2196F3','#FFEB3B', '#4CAF50', '#FF9800', '#AABBCC'];
        const data = [];
        if (this.state.data != null)
            this.state.data.forEach(value=> data.push(+value.score));
        return(
        <View style={styles.container}>
            <Button
                icon={
                    <Icon
                        name='refresh'
                        size={15}
                        color='blue'
                    />
                }
                title='Refresh'
                onPress={()=>{
                    //todo investigate
                    NetInfo.isConnected.fetch().then(isConnected => {
                        if (isConnected) {
                            this.getStorage().catch(error => {
                                console.log("Failed to get data", error);
                                Alert.alert("Error", "Error while loading offline data");
                            });
                        }
                        else {
                            this.getAsyncData().catch(error=> {
                                console.log("Failed to get data", error);
                                Alert.alert("Error", "Error while loading offline data");
                            });
                        }
                    });
                }}
            />
            <Text>Cartoons list</Text>
            <FlatList
                style={styles.viewList}
                data={this.state.data}
                keyExtractor = {(key, value) => value}
                renderItem={({item}) =>
                    <Text>
                    {`Name: ${item.title} Score: ${item.score}`}
                    </Text>
                }
            />
            <PieChart chart_wh={250} series={data} sliceColor={sliceColor}/>
            <Button title="Add" onPress={()=>{
                NetInfo.isConnected.removeEventListener(
                    'connectionChange', this.getToken
                );
                this.props.navigation.navigate("AddRT");}}/>
            <Divider/>
        </View>);
    }
}



const styles = StyleSheet.create({
    container:{
        backgroundColor: "#B8D8D8",
    },
    viewList: {
        maxHeight: 140,

    },
    text: {
        backgroundColor: "blue",
        paddingTop: 20,
        paddingBottom: 20
    }
});