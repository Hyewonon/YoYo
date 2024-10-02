import React, { useEffect, useState } from "react";
import { FlatList, View } from "react-native";
import Container from "../../../components/common/Container";
import EventListCard from "./../../../components/card/Event/EventListCard";
import Button from "../../../components/common/Button";
import YoYoText from "../../../constants/YoYoText";
import { getEventList } from "../../../apis/https/eventApi";

export default function EventList({ navigation }) {
    const [eventList, setEventList] = useState([]);
    function clickEvent(id) {
        navigation.navigate("EventDetail", { id: id });
    }
    function registEvent() {
        navigation.navigate("EventRegist");
    }
    function renderItem({ item }) {
        return <EventListCard event={item} onPress={() => clickEvent(item.eventId)} />;
    }
    useEffect(() => {
        async function fetchEventList() {
            try {
                const list = await getEventList();
                setEventList(list)
            } catch (error) {
            }
        }
        fetchEventList()
    }, [])
    if (!eventList) {
        return <View><YoYoText>없어</YoYoText></View>
    }
    return (
        <>
            <Container>
                {eventList.length > 0 ? <FlatList
                    data={eventList}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.eventId}
                /> : <YoYoText type="subTitle" bold center>없어요.</YoYoText>}
            </Container>
            <Button type="fill" width="100%" onPress={registEvent} radius={0}>
                <YoYoText type="subTitle" bold>
                    경조사 추가하기
                </YoYoText>
            </Button>
        </>
    );
}
