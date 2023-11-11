// App.tsx
import React, { Component, useState } from 'react';
import { View, Text, Button, FlatList, StyleSheet, TouchableHighlight, Image } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Pressable } from 'react-native';

const Tab = createBottomTabNavigator();

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
                {this.state.pressing
                    ? <Image style={{ width: 800, height: 600 }} source={{ uri: `https://source.unsplash.com/random/800x600?q=${Date()}` }} />
                    : <TouchableHighlight
                        onPressIn={this._onPressIn}
                        onPressOut={this._onPressOut}
                        style={styles.touchable2}
                    >
                        <View style={styles.button}>
                            <Text style={styles.welcome}>
                                НАЖМИ!
                            </Text>
                        </View>
                    </TouchableHighlight>
                }
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
    touchable2: { borderRadius: 0 },
    button: {
        backgroundColor: "#FFA300",
        borderRadius: 0,
        height: 240,
        width: 200,
        justifyContent: "center",
    },
    row: {
        fontSize: 24,
        padding: 42,
        borderWidth: 1,
        borderColor: '#DDDDDD',
    },
    touchable: { backgroundColor: getRandomColor() },
});

function getRandomColor() {
    return '#' + Math.floor(Math.random() * 16777215).toString(16);
}


const listStyle = StyleSheet.create({
    container: {
        flex: 1,
        padding: 42,
        backgroundColor: 'grey',
        borderWidth: 1,
        borderRadius: 48,
        borderColor: '#DDDDDD',
        margin: 5
    },
    title: {
        fontSize: 18,
        fontStyle: 'italic',
        color: '#000000',
        textDecorationLine: 'underline'
    },
    desc: {
        fontSize: 14,
        color: '#000000',
        marginTop: 10,
        flexWrap: 'wrap'
    },
    img: {
        width: 75,
        height: 110
    }
});

class Screen1 extends Component {
    state = {
        books: [
            {
                name: "Совершенный код",
                description: "Книга представляет собой практическое руководство по разработке программного обеспечения.",
                iconUrl: "https://img3.labirint.ru/rc/e22344ed2dcfdc8fb85606bfdde1065e/363x561q80/books28/272529/cover.jpg?1563607723"
            },
            {
                name: "Гарри Поттер и Дары Смерти",
                description: "Гарри Поттера ждёт самое страшное испытание в жизни — смертельная схватка c Волан-де-Мортом. Ждать помощи не от кого — Гарри одинок, как никогда… Друзья и враги Гарри предстают в совершенно неожиданном свете.",
                iconUrl: "https://www.moscowbooks.ru/image/book/495/orig/i495208.jpg?cu=20180101000000"
            },
            {
                name: "Повелители DOOM",
                description: "DOOM - это не просто игра. Это живая легенда. 25 лет назад она покорила десятки миллионов геймеров и изменила индустрию видеоигр навсегда.",
                iconUrl: "https://img3.labirint.ru/rc/a41397e05068c484c9a20b68367b950e/363x561q80/books73/723053/cover.jpg?1589441103"
            },
        ],
    };

    removeBook = (bookName) => {
        this.setState((prevState) => ({
            books: prevState.books.filter((book) => book.name !== bookName),
        }));
    };

    render() {
        return (
            <View>
                <FlatList
                    data={this.state.books}
                    renderItem={({ item }) => (
                        <ListItem obj={item} onRemove={this.removeBook} />
                    )}
                />
            </View>
        );
    }
}

export default function ListItem({ obj, onRemove }) {
    const [color, setColor] = useState(getRandomColor());

    const onPress = () => {
        setColor(getRandomColor());
    }

    const onLongPress = () => {
        // Call the removeBook function passed from the parent component
        onRemove(obj.name);
    };

    return (
        <Pressable onPress={onPress} onLongPress={onLongPress}>
            <View style={[listStyle.container, { backgroundColor: color }]}>
                <Image source={{ uri: obj.iconUrl }}
                    style={listStyle.img}
                />
                <Text style={listStyle.title}>
                    {obj.name}
                </Text>
                <Text style={listStyle.desc}>
                    {obj.description}
                </Text>
            </View>
        </Pressable>
    );
}

export default function App() {
    return (
        <NavigationContainer>
            <Tab.Navigator>
                <Tab.Screen name="Screen1" component={Screen1} options={{ title: 'Экран 1' }} />
                <Tab.Screen name="Screen2" component={Screen2} options={{ title: 'Экран 2' }} />
            </Tab.Navigator>
        </NavigationContainer>
    );
}
