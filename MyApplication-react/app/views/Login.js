import React from 'react';
import {Alert,
    AsyncStorage,
    ImageBackground,
    StyleSheet,
    Text,
    TextInput,
    TouchableHighlight,
    View,
    ActivityIndicator,
    NetInfo} from 'react-native';

const LOGIN_URL = 'http://192.168.43.185:8080/login';


export class Login extends React.Component{
    static navigationOptions = {
        header : null
    };
    constructor(props)
    {
        super(props);
        this.state = {
            storedUser: null,
            user: 'Username',
            password: 'Password',
            token: null,
            loggedIn: false,
        }
    }

    storeUser = async () => {
        try {
            await AsyncStorage.setItem('user', JSON.stringify({
                'user' : this.state.user,
                'password' : this.state.password
            }));
        } catch (error) {
            console.log(error);
        }
    };

    getUser = async () => {
        try {
            let user = await AsyncStorage.getItem('user');
            user = JSON.parse(user);
            if (user.user === this.state.user && user.password === this.state.password) {
                this.setState({loggedIn: false});
                this.props.navigation.navigate('HomeRT');
            }
            else
                Alert.alert("Error", "Invalid username/password");
            this.setState({loggedIn: false});
        } catch (error) {
            console.log(error);
        }
    };

    storeToken = async () => {
        try {
            await AsyncStorage.setItem('auth-token', this.state.token);
        } catch (error) {
            console.log(error);
        }
    };

    login = ()=> {
        this.setState({loggedIn: true});
        NetInfo.isConnected.fetch().then(isConnected => {
            if (isConnected) {
                fetch(LOGIN_URL, {
                    method: 'POST',
                    headers: {
                        Accept: 'application/json',
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: this.state.user,
                        password: this.state.password,
                    })
                }).then((response) => {
                    const data = response.json();
                    if (response.status === 200) {
                        this.setState({token: response.headers.map.authorization});
                        const value = this.storeToken().catch((error) => {
                            console.log("Found error" + error);
                        });
                        this.storeUser().catch((error)=> {
                            console.log("Error storing user" + error);
                        });
                        if (this.state.token !== null) {
                            this.props.navigation.navigate('HomeRT');
                        }
                        this.setState({loggedIn: false});
                    }
                    else {
                        this.setState({loggedIn: false});
                        Alert.alert("Error", "Invalid username/password");
                    }
                }).catch((error) => {
                    Alert.alert("Error", "Network error");
                    console.log(error);
                    this.getUser().catch(error=>{console.log(error)});
                    this.setState({loggedIn: false});
                });
            }
            else {
                this.getUser().catch(error => {
                    Alert.alert("error", error);
                });
                this.setState({loggedIn: false});
            }
            });
            };

    render()
    {
        const {navigate} = this.props.navigation;
        return(
            <View style = {styles.head}>
                <ImageBackground style = {styles.bgImg}
                                 source={require('../resources/login-background.jpg')}>
                    <Text style = {styles.title}>Insert credentials</Text>
                    <TextInput style = {styles.input}
                               onChangeText={(text) => this.setState({user: text})}
                    />
                    <TextInput
                    secureTextEntry={true}
                    style = {styles.input}
                    onChangeText={(text) => this.setState({password: text})}
                    type = 'password'/>
                    <TouchableHighlight onPress = {this.login}  underlayColor = 'grey'>
                        <Text style = {styles.button}
                              >Login</Text>
                </TouchableHighlight>
                    {
                        this.state.loggedIn &&
                        <ActivityIndicator/>
                    }
                </ImageBackground>
            </View>
        )
    }

}
const styles = StyleSheet.create({
    head:{
        flex: 1
    },
    title: {
        paddingTop: '50%',
        textAlign: 'center',
        fontSize: 20,
        color: 'white'
    },
    input: {
        color: 'white',
        marginTop: 20,
        textAlign: 'center',
        marginLeft: '23%',
        justifyContent: 'center',
        width: 200,
        // color: 'grey'
    },

    button: {
        paddingTop:20,
        paddingBottom:20,
        textAlign: 'center',
        color: 'white'
    },

    bgImg: {
        width: undefined,
        height: undefined,
        flex: 2
    }
});