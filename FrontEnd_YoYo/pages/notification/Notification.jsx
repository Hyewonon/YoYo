import { View, Text, StyleSheet, FlatList } from "react-native";
import React, { useState } from "react";
import Container from "../../components/common/Container";
import SelectTap from "../../components/common/SelectTap";
import NotificationCard from "../../components/notification/NotificationCard";
import { MainStyle } from "../../constants/style";
import ScheduleCard from "../../components/notification/ScheduleCard";

export default function Notification() {
    const [isLeft, setIsLeft] = useState(true);

    const DATA = [
        {
            id: "1",
            date: "2024.09.19",
            title: "나의 결혼식",
            name: "김현태",
            description: "직장 동료",
            tag: "company",
        },
        {
            id: "2",
            date: "2024.09.19",
            title: "출소",
            name: "이찬진",
            description: "고등학교 친구",
            tag: "friend",
        },
        {
            id: "3",
            date: "2024.09.19",
            title: "주먹 대잔치",
            name: "최광림",
            description: "주먹왕",
            tag: "family",
        },
    ];

    const [data, setData] = useState(DATA);

    const DATA2 = [
        {
            id: "3",
            date: "2024.09.19 13:00",
            name: "김현태",
        },
        {
            id: "4",
            date: "2024.09.19 12:00",
            name: "이찬진",
        },
        {
            id: "5",
            date: "2024.09.19 12:00",
            name: "최광림",
        },
    ];

    const [data2, setData2] = useState(DATA2);

    function clickScheduleButton(id) {
        setData((prevData) => prevData.filter((item) => item.id !== id));
    }
    function clickNotification(id) {
        setData2((prevData) => prevData.filter((item) => item.id !== id));
    }
    function renderScheduleItem({ item }) {
        return <ScheduleCard item={item} onPress={clickScheduleButton} />;
    }
    function renderNotificationItem({ item }) {
        return <NotificationCard item={item} onLongPress={clickNotification} />;
    }

    const notificationList = () => {
        if (isLeft) {
            return (
                <FlatList
                    data={data}
                    renderItem={renderScheduleItem}
                    keyExtractor={(item) => item.id}
                ></FlatList>
            );
        } else {
            return (
                <FlatList
                    data={data2}
                    renderItem={renderNotificationItem}
                    keyExtractor={(item) => item.id}
                ></FlatList>
            );
        }
    };

    return (
        <View style={styles.container}>
            <View style={styles.tapContainer}>
                <SelectTap
                    left={"일정"}
                    right={"마음"}
                    leftColor={MainStyle.colors.main}
                    rightColor={MainStyle.colors.main}
                    stateHandler={setIsLeft}
                />
            </View>
            <Container>{notificationList()}</Container>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: MainStyle.colors.white,
    },
    tapContainer: {
        marginVertical: 16,
    },
});
