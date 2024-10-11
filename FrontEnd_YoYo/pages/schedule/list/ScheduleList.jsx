import React, { useEffect, useState } from "react";
import { View, Pressable, FlatList, StyleSheet } from "react-native";
import { MainStyle } from "../../../constants/style";
import IconButton from "../../../components/common/IconButton";
import Header from "../../../components/header/Header";
import EventScheduleCard from "../../../components/card/Event/EventScheduleCard";
import YoYoText from "../../../constants/YoYoText";
import { getSchedule } from "../../../apis/https/scheduleApi";
import { formatDate } from "../../../util/date";

export default function ScheduleList({ navigation }) {
    const [expandedDate, setExpandedDate] = useState(null);
    const [data, setData] = useState([]);

    // API 호출 및 데이터 로드
    useEffect(() => {
        async function fetchSchedule() {
            try {
                const response = await getSchedule(); // API 호출
                if (response.length === 0) {
                    setData();
                    return;
                }
                setData(
                    response
                        .reduce((acc, current) => {
                            const date = formatDate(current.startAt); // startAt을 기준으로 그룹화
                            const dateTimestamp = new Date(
                                current.startAt
                            ).getTime();
                            const event = {
                                oppositeId: current.memberId,
                                eventId: current.eventId,
                                title: current.title,
                                name: current.name,
                                location: current.location,
                                startAt: formatDate(current.startAt),
                                endAt: formatDate(current.endAt),
                            };

                            // 이미 해당 날짜에 대한 객체가 있는지 확인
                            const existingDate = acc.find(
                                (item) => item.date === date
                            );

                            if (existingDate) {
                                // 해당 날짜가 존재하면 events 배열에 이벤트 추가
                                existingDate.events.push(event);
                            } else {
                                // 해당 날짜가 없으면 새로운 객체 생성 후 추가
                                acc.push({
                                    date: date,
                                    dateTimestamp: dateTimestamp,
                                    events: [event],
                                });
                            }

                            return acc;
                        }, [])
                        .sort((a, b) => a.dateTimestamp - b.dateTimestamp)
                );
            } catch (error) {
                console.error("Error fetching schedule:", error);
            }
        }

        fetchSchedule(); // 컴포넌트가 처음 렌더링될 때 호출
    }, []);

    function clickSchedule({ item }) {
        navigation.navigate("ScheduleDetail", {
            item: item,
        });
    }
    function toggleExpand(date) {
        if (expandedDate === date) {
            setExpandedDate(null);
        } else {
            setExpandedDate(date);
        }
    }
    function renderItem({ item }) {
        return (
            <EventScheduleCard
                event={item}
                onPress={() => {
                    clickSchedule({ item });
                }}
            />
        );
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

            {data && data.length > 0 ? (
                <FlatList
                    data={data}
                    renderItem={scheduleList}
                    keyExtractor={(item) => item.date}
                />
            ) : (
                <YoYoText type="subTitle" bold center>
                    등록된 일정이 없어요.
                </YoYoText>
            )}
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
