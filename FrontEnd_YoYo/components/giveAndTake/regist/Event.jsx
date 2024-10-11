import { View, StyleSheet, FlatList, Pressable } from "react-native";
import React, { useEffect, useState } from "react";
import { MainStyle } from "../../../constants/style";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../common/Input";
import EventScheduleCard from "../../card/Event/EventScheduleCard";
import { searchEvent } from "../../../apis/https/eventApi";
import { formatDate } from "../../../util/date";

export default function Event({ type, person, setPerson, setIsActive }) {
    const [eventName, setEventName] = useState("");
    const [cardId, setCardId] = useState(0);
    const [eventData, setEventData] = useState([]);
    function clickCard({ item }) {
        if (item.eventId === cardId) {
            setCardId(0);
            setIsActive(false);
        } else {
            setCardId(item.eventId);
            setPerson((prevPerson) => ({
                ...prevPerson,
                eventId: item.eventId,
                eventName: item.eventName,
            }));
            setIsActive(true);
        }
    }
    useEffect(() => {
        if (eventName.length > 0) {
            setIsActive(true);
            setPerson((prevPerson) => ({
                ...prevPerson,
                eventName: eventName,
            }));
        }
    }, [eventName]);
    useEffect(() => {
        async function fetchEventData(eventName) {
            const response = await searchEvent(eventName);
            const tmpData = response.map((item) => ({
                eventId: item.eventId,
                title: item.title,
                location: item.location,
                startAt: formatDate(item.startAt),
                endAt: formatDate(item.endAt),
            }));
            setEventData(tmpData);
        }
        fetchEventData(eventName);
    }, [eventName]);
    function renderedItem({ item }) {
        return (
            <EventScheduleCard
                type="select"
                onPress={() => clickCard({ item })}
                event={item}
                selectedCard={cardId}
            />
        );
    }

    return (
        <View>
            <View style={styles.container}>
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        행사명
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input
                        text={eventName}
                        onChange={setEventName}
                        placeholder={"행사명을 기록해주세요"}
                    />
                </View>
            </View>
            <View style={styles.container}>
                {type === 1 && eventData.length > 0 && (
                    <FlatList
                        data={eventData}
                        renderItem={renderedItem}
                        style={styles.innerContainer}
                        keyExtractor={(item) => item.eventId}
                    />
                )}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        marginTop: 30,
    },
    textContainer: {
        marginTop: 30,
    },
    tagContainer: {
        marginVertical: 12,
    },
    innerContainer: {
        marginBottom: 16,
    },
});
