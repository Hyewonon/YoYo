import { View, StyleSheet } from "react-native";
import React from "react";
import Card from "../Card";
import YoYoText from "../../../constants/YoYoText";
import { CheckBox } from "../../common/CheckBox";

export default function EventScheduleCard({
    event,
    onPress,
    type,
    selectedCard,
}) {
    return (
        <Card height={120} onPress={onPress}>
            <View style={styles.container}>
                <View style={styles.innerContainer}>
                    <View style={styles.titleContainer}>
                        <View style={styles.selectContainer}>
                            <YoYoText type="subTitle" bold>
                                {event.title}
                            </YoYoText>
                            {type === "select" && (
                                <CheckBox
                                    checked={event.eventId === selectedCard}
                                />
                            )}
                        </View>
                        <YoYoText type="md" bold>
                            {event.name}
                        </YoYoText>
                    </View>
                    <YoYoText type="content">{event.startAt}</YoYoText>
                </View>
                <YoYoText type="desc">{event.location}</YoYoText>
            </View>
        </Card>
    );
}
const styles = StyleSheet.create({
    container: {
        gap: 12,
        justifyContent: "center",
    },
    innerContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
    },
    titleContainer: {
        gap: 3,
    },
    selectContainer: {
        flexDirection: "row",
        alignItems: "center",
        gap: 6,
    },
});
