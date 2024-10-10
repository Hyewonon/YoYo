import { View, Text, StyleSheet, FlatList } from "react-native";
import React, { useLayoutEffect, useState } from "react";
import Container from "./../../../components/common/Container";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import SearchBar from "./../../../components/common/SearchBar";
import SelectTap from "../../../components/common/SelectTap";
import EventBeforeRegist from "../../../components/card/Event/EventBeforeRegist";
import EventAfterRegist from "../../../components/card/Event/EventAfterRegist";
import IconButton from "../../../components/common/IconButton";
import TagList from "../../../components/common/TagList";
import Button from "./../../../components/common/Button";

const event = {
    eventId: 1,
    totalReceiver: 5,
    totalReceivedAmount: 1000000,
    eventTitle: "이벤트1",
    transactions: [
        {
            id: 1,
            name: "이찬진",
            tag: "friend",
            detail: "고등학교 친구",
            date: "2024.09.11",
            price: 50000,
        },
        {
            id: 2,
            name: "이찬진",
            tag: "friend",
            detail: "고등학교 친구",
            date: "2024.09.11",
            price: 50000,
        },
        {
            id: 3,
            name: "이찬진",
            tag: "friend",
            detail: "고등학교 친구",
            date: "2024.09.11",
            price: 50000,
        },
        {
            id: 4,
            name: "이찬진",
            tag: "friend",
            detail: "고등학교 친구",
            date: "2024.09.11",
            price: 50000,
        },
        {
            id: 5,
            name: "이찬진",
            tag: "friend",
            detail: "고등학교 친구",
            date: "2024.09.11",
            price: 50000,
        },
    ],
};

export default function EventDetail({ navigation }) {
    const [selectedTag, setSelectedTag] = useState("all");
    const [isWait, setIsWait] = useState(true);

    function clickWaitCard(friend) {
        navigation.navigate("지인선택", { friend: friend });
    }
    function clickTag(type) {
        setSelectedTag(type);
    }
    function clickBottomButton() {
        if (isWait) {
            navigation.navigate("SelectRegistType", { eventId: event.eventId });
        } else {
            navigation.navigate("SelectLinkType", {
                eventId: event.eventId,
            });
        }
    }
    const renderItem = ({ item }) => {
        return isWait ? (
            <EventBeforeRegist
                event={item}
                onPress={() => clickWaitCard(item)}
            />
        ) : (
            <EventAfterRegist event={item} />
        );
    };

    useLayoutEffect(() => {
        navigation.setOptions({
            title: event.eventTitle,
            headerRight: () => (
                <IconButton
                    icon="create-outline"
                    size={24}
                    onPress={() => alert("Header Button Pressed")}
                />
            ),
        });
    }, [navigation]);

    return (
        <>
            <Container>
                <View style={styles.rowContainer}>
                    <YoYoText
                        type="subTitle"
                        bold
                        color={MainStyle.colors.main}
                    >
                        {event.totalReceiver}
                    </YoYoText>
                    <YoYoText type="md">명의 분들이</YoYoText>
                </View>
                <View style={styles.rowContainer}>
                    <YoYoText
                        type="subTitle"
                        bold
                        color={MainStyle.colors.main}
                    >
                        {event.totalReceivedAmount.toLocaleString()}
                    </YoYoText>
                    <YoYoText type="md">원의 마음을 전해주었어요.</YoYoText>
                </View>
                <SearchBar placeholder="이름을 입력해주세요." />
                <TagList
                    onPress={clickTag}
                    selectedTag={selectedTag}
                    size={64}
                    all
                />
                <View style={{ position: "relative", flex: 1 }}>
                    <SelectTap
                        leftColor={MainStyle.colors.main}
                        rightColor={MainStyle.colors.main}
                        stateHandler={setIsWait}
                        left="등록 대기"
                        right="등록 완료"
                    />
                    <FlatList
                        data={event.transactions}
                        renderItem={renderItem}
                        keyExtractor={(item) => item.id.toString()}
                    />
                    <Text style={styles.waitText}>5</Text>
                </View>
            </Container>
            <Button
                type="fill"
                radius={0}
                onPress={clickBottomButton}
                width="100%"
            >
                <YoYoText type="md" bold>
                    {isWait ? "직접 등록" : "마음 받으실 곳"}
                </YoYoText>
            </Button>
        </>
    );
}

const styles = StyleSheet.create({
    rowContainer: {
        flexDirection: "row",
        alignItems: "center",
        marginBottom: 8,
        gap: 4,
    },
    tagContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        marginVertical: 16,
    },
    transactionContainer: {
        padding: 10,
        borderBottomWidth: 1,
        borderBottomColor: "#ddd",
    },
    transactionName: {
        fontWeight: "bold",
    },
    waitText: {
        color: "white",
        backgroundColor: "red",
        paddingVertical: 1,
        paddingHorizontal: 7,
        borderRadius: 100,
        fontWeight: "bold",
        textAlign: "center",
        position: "absolute",
        top: 11,
        left: "40%",
    },
});
