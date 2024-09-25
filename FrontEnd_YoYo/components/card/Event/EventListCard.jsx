import { View, StyleSheet } from "react-native";
import React from "react";
import Card from "../Card";
import YoYoText from "../../../constants/YoYoText";
import { CheckBox } from "../../common/CheckBox";

export default function EventListCard({ event, onPress, type, selectedCard }) {
    return (
        <Card height={96} onPress={onPress}>
            <View style={styles.container}>
                <View style={styles.innerContainer}>
                    <View style={styles.titleContainer}>
                        <YoYoText type="subTitle" bold>
                            {event.name}
                        </YoYoText>
                        {type === "select" && (
                            <CheckBox checked={event.id === selectedCard} />
                        )}
                    </View>
                    <YoYoText type="content">{event.date}</YoYoText>
                </View>
                <YoYoText type="desc">{event.position}</YoYoText>
            </View>
        </Card>
    );
}
const styles = StyleSheet.create({
    innerContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "flex-start",
    },
    container: {
        gap: 12,
    },
    titleContainer: {
        flexDirection: "row",
        gap: 6,
    },
});
