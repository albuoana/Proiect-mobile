import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
export class Header extends React.Component{
    constructor(props){
        super(props);
        this.state = { isLoggedIn: false};
    }
    toggleUser = ()=> this.setState(previousState => {
        return { isLoggedIn: !previousState.isLoggedIn };
    });
    render(){
        let display = this.state.isLoggedIn ? 'Logged, yay' : this.props.message;
        return (
            <View style={styles.headStyle}>
            <Text style = {styles.login}
                  onPress={this.toggleUser}> {display} </Text>
            </View>
        )
    }
}
const styles = StyleSheet.create({
    login:{
        textAlign: 'right',
        backgroundColor: '#ffffff',
        fontSize: 20
    // flex:3
    },
    headStyle:{
        flex: 1,
        backgroundColor: "yellow",
        paddingTop:30,
        paddingRight:10
    }
});

