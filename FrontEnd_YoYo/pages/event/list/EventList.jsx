import React, { useCallback, useState } from "react";
import { FlatList, View } from "react-native";
import Container from "../../../components/common/Container";
import EventListCard from "./../../../components/card/Event/EventListCard";
import Button from "../../../components/common/Button";
import YoYoText from "../../../constants/YoYoText";
import { getEventList } from "../../../apis/https/eventApi";
import { useFocusEffect } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import LottieView from "lottie-react-native";

export default function EventList({ navigation }) {
    const [eventList, setEventList] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    async function clickEvent(id) {
        try {
            await AsyncStorage.setItem("eventId", id.toString());
            navigation.navigate("EventDetail", { id });
        } catch (error) {
            console.error("Error storing the event ID:", error);
        }
    }
    function registEvent() {
        navigation.navigate("EventRegist");
    }
    function renderItem({ item }) {
        return (
            <EventListCard
                event={item}
                onPress={() => clickEvent(item.eventId)}
            />
        );
    }
    useFocusEffect(
        useCallback(() => {
            async function fetchEventList() {
                try {
                    const list = await getEventList();
                    setEventList(list);
                    setIsLoading(false);
                } catch (error) {
                    // 오류 처리
                }
            }
            fetchEventList();
        }, [])
    );
    if (!eventList) {
        return (
            <View>
                <YoYoText>없어</YoYoText>
            </View>
        );
    }
    return (
        <>
            {isLoading ? (
                <>
                    <LottieView
                        source={require("../../../assets/loadingIcon.json")}
                    />
                </>
            ) : (
                <>
                    <Container>
                        {eventList.length > 0 ? (
                            <FlatList
                                data={eventList}
                                renderItem={renderItem}
                                keyExtractor={(item) => item.eventId}
                            />
                        ) : (
                            <YoYoText type="subTitle" bold center>
                                경조사가 없어요.
                            </YoYoText>
                        )}
                    </Container>
                    <Button
                        type="fill"
                        width="100%"
                        onPress={registEvent}
                        radius={0}
                    >
                        <YoYoText type="md" bold>
                            경조사 추가하기
                        </YoYoText>
                    </Button>
                </>
            )}
        </>
    );
}
