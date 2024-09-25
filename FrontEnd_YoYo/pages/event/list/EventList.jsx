import React from "react";
import { FlatList } from "react-native";
import Container from "../../../components/common/Container";
import EventListCard from "./../../../components/card/Event/EventListCard";
import Button from "../../../components/common/Button";
import YoYoText from "../../../constants/YoYoText";

const eventList = [
    {
        id: 1,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 2,
        name: "장례식",
        date: "24.09.04",
        position: "서울시 강남구",
    },
    {
        id: 3,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 4,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 5,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 6,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 7,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 8,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 9,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
    {
        id: 10,
        name: "결혼식",
        date: "24.08.29",
        position: "서울시 강남구",
    },
];

const before = {
    name: "이찬진",
    date: "24.08.29",
    detail: "고등학교 친구",
    price: "34000",
};

const after = {
    name: "이찬진",
    date: "24.08.29",
    detail: "고등학교 친구",
    price: "34000",
    tag: "friend",
};

export default function EventList({ navigation }) {
    function clickEvent() {
        navigation.navigate("EventDetail");
    }
    function registEvent() {
        navigation.navigate("EventRegist");
    }
    function renderItem({ item }) {
        return <EventListCard event={item} onPress={clickEvent} />;
    }

    return (
        <>
            <Container>
                <FlatList
                    data={eventList}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.id}
                />
            </Container>
            <Button type="fill" width="100%" onPress={registEvent} radius={0}>
                <YoYoText type="subTitle" bold>
                    경조사 추가하기
                </YoYoText>
            </Button>
        </>
    );
}
