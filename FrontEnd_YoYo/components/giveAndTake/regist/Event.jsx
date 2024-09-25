import { View, StyleSheet, FlatList, Pressable } from "react-native";
import React, { useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../common/Input";
import { MainStyle } from "../../../constants/style";
import EventScheduleCard from "../../card/Event/EventScheduleCard";

export default function Event({ type, person, data, setIsActive }) {
    const [eventName, setEventName] = useState("");
    const [selectedCard, setSelectedCard] = useState(-1);
    function clickCard(id) {
        if (id === selectedCard) {
            setSelectedCard(-1);
            setIsActive(false);
        } else {
            setSelectedCard(id);
            setIsActive(true);
        }
    }
    function renderedItem(item) {
        return (
            <EventScheduleCard
                type="select"
                onPress={() => clickCard(item.item.id)}
                event={item.item}
                selectedCard={selectedCard}
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
                {type === 1 && data.length > 0 && (
                    <FlatList
                        data={data}
                        renderItem={renderedItem}
                        style={styles.innerContainer}
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
