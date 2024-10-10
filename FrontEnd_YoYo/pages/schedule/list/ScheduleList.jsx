import React, { useState } from "react";
import { View, Text, Pressable, FlatList, StyleSheet } from "react-native";
import { MainStyle } from "../../../constants/style";
import IconButton from "../../../components/common/IconButton";
import Header from "../../../components/header/Header";
import EventScheduleCard from "../../../components/card/Event/EventScheduleCard";
import YoYoText from "../../../constants/YoYoText";
const eventsData = [
    {
        // 이거 어떻게 가져올지 고민해 봐야 함
        date: "2024.09.02",
        events: [
            {
                eventId: 1,
                title: "이벤트 이름",
                name: "주최자 이름",
                position: "주소",
                date: "2024.09.02",
                endAt: "2024.09.02",
            },
            {
                eventId: 2,
                title: "이벤트 이름",
                name: "주최자 이름",
                position: "주소",
                date: "2024.09.02",
                endAt: "2024.09.02",
            },
            {
                eventId: 3,
                title: "이벤트 이름",
                name: "주최자 이름",
                position: "주소",
                date: "2024.09.02",
                endAt: "2024.09.03",
            },
        ],
    },
    {
        date: "2024.09.03",
        events: [
            {
                eventId: 4,
                title: "이벤트 이름",
                name: "주최자 이름",
                position: "주소",
                date: "2024.09.03",
                endAt: "2024.09.04",
            },
        ],
    },
];

export default function ScheduleList({ navigation }) {
    const [expandedDate, setExpandedDate] = useState(null);

    function clickSchedule() {
        navigation.navigate("ScheduleDetail");
    }
    function toggleExpand(date) {
        if (expandedDate === date) {
            setExpandedDate(null);
        } else {
            setExpandedDate(date);
        }
    }
    function renderItem({ item }) {
        return <EventScheduleCard event={item} onPress={clickSchedule} />;
    }
    const scheduleList = ({ item }) => (
        <View>
            <Pressable
                onPress={() => toggleExpand(item.date)}
                style={({}) => [styles.dateContainer]}
            >
                <YoYoText type={"md"} bold>
                    {item.date}
                </YoYoText>
                <View style={styles.dateRightContainer}>
                    <YoYoText type={"md"}>{item.events.length}개</YoYoText>
                    <IconButton
                        icon={
                            expandedDate === item.date
                                ? "chevron-up-sharp"
                                : "chevron-down-sharp"
                        }
                        onPress={() => toggleExpand(item.date)}
                    />
                </View>
            </Pressable>

            {expandedDate === item.date && (
                <View style={styles.scheduleListContainer}>
                    <FlatList
                        data={item.events}
                        renderItem={renderItem}
                        keyExtractor={(item) => item.eventId}
                    />
                </View>
            )}
        </View>
    );

    return (
        <View style={styles.outerContainer}>
            <View style={styles.container}>
                <Header />
            </View>
            <View>
                <FlatList
                    data={eventsData}
                    renderItem={scheduleList}
                    keyExtractor={(item) => item.date}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    outerContainer: {
        backgroundColor: MainStyle.colors.white,
        flex: 1,
    },
    container: {
        padding: 24,
    },
    dateContainer: {
        padding: 16,
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        backgroundColor: MainStyle.colors.hover,
    },
    dateRightContainer: {
        flexDirection: "row",
        columnGap: 10,
        justifyContent: "space-around",
        alignItems: "center",
    },
    scheduleListContainer: {
        padding: 16,
        backgroundColor: MainStyle.colors.white,
    },
    header: {
        marginVertical: 24,
    },
    subTitle: {
        alignItems: "center",
        marginVertical: 16,
    },
});
