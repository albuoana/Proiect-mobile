import React from "react";
import {Alert, AsyncStorage, View, TextInput, Text, Button, StyleSheet, NetInfo} from "react-native";
import {CheckBox} from 'react-native-elements'
const URL = "http://192.168.43.185:8080/api/v1/cartoons";
export class Add extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            token: null,
            title: null,
            score: null,
            description: false,
            episodes: null,
            added: false
        };
    };

    changeLocalData = async(data) => {
        try {
            await AsyncStorage.removeItem('data');
            await AsyncStorage.setItem('data', data);
        } catch (e) {
            console.log("Error", e);
        }
    };

    persistLocalData = async() => {
        try {
            await AsyncStorage.getItem('data').then((result, er)=>{
                let temp = [];
                result = JSON.parse(result);
                if (result != null)
                    result.forEach(value=> temp.push(value));
                temp.push({
                    title: this.state.title,
                    description: this.state.description,
                    score: this.state.score,
                    episodes: this.state.episodes,
                    added: this.state.added
                });

                this.changeLocalData(JSON.stringify(temp)).catch(error=>{
                    console.log("Error", error);
                });
                this.props.navigation.navigate("HomeRT");
            });


        } catch (e) {
            console.log("error: " + e);
            Alert.alert("Error", "Error saving offline data");
        }
    };


    addAnime = async () => {
        try {
            const value = await AsyncStorage.getItem('auth-token');
            this.state.token = value;
            this.add();
        }
        catch (e) {
            console.log("Can't find token " + e);
        }
    };

    add = ()=>fetch(
        URL, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': this.state.token,
            },
            body:JSON.stringify({
                "title": this.state.title,
                "description": this.state.description,
                "score": this.state.score,
                "episodes": this.state.episodes,
                "added": this.state.added
            })
        }
    ).then(res=>res.json()).then(res=>{console.log(res);
        this.props.navigation.navigate("HomeRT");
    }).catch((error) => {
        console.log(error);
    });

    render() {
        return(<View>
            <Text style={styles.title}>Add new cartoon</Text>
            <Text>Title</Text>
                <TextInput
                onChangeText={(text) => this.setState({title: text})}/>
            <Text>Description</Text>
            <TextInput
                onChangeText={(text) => this.setState({description: text})}/>
            <Text>Score</Text>
            <TextInput
                keyboardType = 'numeric'
                onChangeText = {(text)=> this.onScoreChanged(text)}
                value = {this.state.score}
            />
            <Text>Episodes</Text>
            <TextInput
                keyboardType = 'numeric'
                onChangeText = {(text)=> this.onEpisodesChanged(text)}
                value = {this.state.episodes}
            />
            <Button
            title='Save'
            onPress={()=>{
                NetInfo.isConnected.fetch().then((connection) => {
                    if (connection) {
                        this.addAnime().catch((error) => {
                            console.log("Error: " + error);
                        });
                    }
                    this.persistLocalData().catch((error)=> {
                    console.log(error)});
                    }) }}/>
        </View>);
    }

    // code to remove non-numeric characters from text
    onScoreChanged(text) {
        this.setState({score: text});
    }
    onEpisodesChanged(text) {
        this.setState({episodes: text})
    }

}

const styles = StyleSheet.create({
    head:{
        // backgroundImage: 'resources/login-background.jpg'
        flex: 1
    },
    title: {
        paddingTop: '7%',
        textAlign: 'center',
        fontSize: 30,
        color: 'black'
    }
});