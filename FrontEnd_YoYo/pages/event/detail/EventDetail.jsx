import { View, Text, StyleSheet, FlatList, Alert } from "react-native";
import React, {
    useCallback,
    useLayoutEffect,
    useState,
} from "react";
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
import {
    getEventDetail,
    getEventTransaction,
} from "../../../apis/https/eventApi";
import { useFocusEffect } from "@react-navigation/native";
import Loading from "../../../components/common/Loading";
import LoadingSpinner from "../../../components/common/LoadingSpinner";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function EventDetail({ navigation, route }) {
    const [event, setEvent] = useState();
    const [eventList, setEventList] = useState();
    const eventId = route.params.id;
    const [keyword, setKeyword] = useState("");
    const [selectedTag, setSelectedTag] = useState("all");
    const [waitCnt, setWaitCnt] = useState(0);
    const [isWait, setIsWait] = useState(true);
    const [isLoading, setIsLoading] = useState(true);
    function clickWaitCard(friend) {
        navigation.navigate("지인선택", {
            friend: friend,
            eventId: event.eventId,
        });
    }
    function clickTag(type) {
        setSelectedTag(type);
    }
    async function clickBottomButton() {
        if (isWait) {
            navigation.navigate("SelectRegistType", { event: event });
        } else {
            const payInfo = await AsyncStorage.getItem("payInfo")
            if (payInfo.balance >= 0) {
                navigation.navigate("SelectLinkType", {
                    event: event,
                });
            } else {
                Alert.alert("페이 정보가 없습니다.", "YoYo페이를 등록해주세요.")
            }
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
    useFocusEffect(
        useCallback(() => {
            let isActive = true;
            setIsLoading(true);
            const fetchData = async () => {
                try {
                    const [eventData, transactionResponse] = await Promise.all([
                        getEventDetail(eventId),
                        getEventTransaction(eventId, keyword, "", !isWait),
                    ]);

                    if (isActive) {
                        setEvent(eventData);
                        if (transactionResponse.status === 200) {
                            const data = transactionResponse.data;
                            if (isWait) setWaitCnt(data.length);

                            const filteredData =
                                selectedTag === "all"
                                    ? data
                                    : data.filter(
                                        (item) =>
                                            item.relationType ===
                                            selectedTag.toUpperCase()
                                    );
                            setEventList(filteredData);
                        } else {
                            setEventList([]);
                        }
                    }
                } catch (error) {
                    console.log(error);
                } finally {
                    if (isActive) setIsLoading(false);
                }
            };

            fetchData();

            return () => {
                isActive = false;
            };
        }, [keyword, selectedTag, isWait, eventId])
    );
    useLayoutEffect(() => {
        if (event) {
            navigation.setOptions({
                title: event.title || "",
                headerRight: () => (
                    <IconButton
                        icon="create-outline"
                        size={24}
                        onPress={() => alert("Header Button Pressed")}
                    />
                ),
            });
        }
    }, [navigation, event]);
    if (!event) return <Loading />;
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
                        {event.totalReceivedAmount}
                    </YoYoText>
                    <YoYoText type="md">원의 마음을 전해주었어요.</YoYoText>
                </View>
                <SearchBar
                    placeholder="이름을 입력해주세요."
                    keyword={keyword}
                    setKeyword={setKeyword}
                />
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
                    {isLoading ? (
                        <LoadingSpinner />
                    ) : eventList && eventList.length > 0 ? (
                        <FlatList
                            data={eventList}
                            renderItem={renderItem}
                            keyExtractor={(item) =>
                                item.transactionId.toString()
                            }
                        />
                    ) : (
                        <View style={{ paddingTop: 24 }}>
                            <YoYoText type="md" bold center>
                                거래 내역이 없습니다.
                            </YoYoText>
                        </View>
                    )}
                    {<Text style={styles.waitText}>{waitCnt}</Text>}
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
    resultContainer: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
});
