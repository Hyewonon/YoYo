import { View, StyleSheet, FlatList, Alert } from "react-native";
import React, { useEffect, useState } from "react";
import Container from "../../components/common/Container";
import SelectTap from "../../components/common/SelectTap";
import NotificationCard from "../../components/notification/NotificationCard";
import { MainStyle } from "../../constants/style";
import ScheduleCard from "../../components/notification/ScheduleCard";
import { getNotification } from "../../apis/https/notificationApi";
import { formatDate } from "../../util/date";
import { registerSchedule } from "../../apis/https/scheduleApi";
import LoadingSpinner from "../../components/common/LoadingSpinner";
import YoYoText from "../../constants/YoYoText";

export default function Notification() {
    const [isLeft, setIsLeft] = useState(true);
    const [notificationData, setNotificationData] = useState([]);
    const type = isLeft ? "EVENT" : "PAY";
    const [isLoading, setIsLoading] = useState(true);

    // API 호출 및 데이터 로드
    useEffect(() => {
        async function fetchNotification() {
            try {
                setIsLoading(true);
                const response = await getNotification(type); // API 호출
                const tmpData = response.map((item) => ({
                    notificationId: item.notificationId,
                    date: formatDate(item.createdAt), // createdAt을 Date 객체로 변환
                    memberId: item.memberId,
                    name: item.name,
                    tag: item.tag.toLowerCase(),
                    description: item.description,
                    eventId: item.eventId,
                    title: item.title,
                    type: item.type,
                }));
                setNotificationData(tmpData);
                setIsLoading(false);
            } catch (error) {
                console.error("Error fetching Notification:", error);
            }
        }
        fetchNotification(); // 컴포넌트가 처음 렌더링될 때 호출
    }, [type]);
    async function clickScheduleButton(notificationId, isRegister) {
        try {
            await registerSchedule(notificationId, isRegister);
            setNotificationData((prevData) =>
                prevData.filter(
                    (item) => item.notificationId !== notificationId
                )
            );
        } catch (error) {
            Alert.alert("일정등록 실패\n 잠시후 다시 시도해 주세요.");
        }
    }
    async function clickNotification(notificationId) {
        try {
            await registerSchedule(notificationId, "false");
            setNotificationData((prevData) =>
                prevData.filter(
                    (item) => item.notificationId !== notificationId
                )
            );
        } catch (error) {
            Alert.alert("알림 삭제 실패.\n 잠시후 다시 시도해 주세요.");
        }
    }
    function renderScheduleItem({ item }) {
        return <ScheduleCard item={item} onPress={clickScheduleButton} />;
    }
    function renderNotificationItem({ item }) {
        return <NotificationCard item={item} onLongPress={clickNotification} />;
    }

    const notificationList = () => {
        if (isLoading) {
            return <LoadingSpinner />;
        } else {
            if (notificationData.length === 0) {
                return (
                    <View style={styles.resultContainer}>
                        <YoYoText type={"title"} bold>
                            알림이 없어요.
                        </YoYoText>
                    </View>
                );
            }
            if (isLeft) {
                return (
                    <FlatList
                        data={notificationData}
                        renderItem={renderScheduleItem}
                        keyExtractor={(item) => item.notificationId}
                    />
                );
            } else {
                return (
                    <FlatList
                        data={notificationData}
                        renderItem={renderNotificationItem}
                        keyExtractor={(item) => item.notificationId}
                    />
                );
            }
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
    resultContainer: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
});
