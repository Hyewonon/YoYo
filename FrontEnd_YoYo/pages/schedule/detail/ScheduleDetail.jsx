import { View, StyleSheet, Alert } from "react-native";
import React, {
    useCallback,
    useEffect,
    useLayoutEffect,
    useState,
} from "react";
import { FlatList } from "react-native-gesture-handler";
import { MainStyle } from "../../../constants/style";
import { getTransaction } from "../../../apis/https/transactionApi";
import Container from "../../../components/common/Container";
import SelectTap from "../../../components/common/SelectTap";
import Button from "../../../components/common/Button";
import DetailMoneyGauge from "../../../components/card/Yoyo/DetailMoneyGauge";
import YoYoCardDetail from "../../../components/card/Yoyo/YoYoCardDetail";
import YoYoText from "../../../constants/YoYoText";
import { formatDate } from "../../../util/date";
import { getRelation } from "../../../apis/https/relationApi";
import { useFocusEffect } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function ScheduleDetail({ route, navigation }) {
    const [giveAndTake, setGiveAndTake] = useState(true);
    const [giveData, setGiveData] = useState([]);
    const [takeData, setTakeData] = useState([]);
    const [data, setData] = useState({
        give: 0,
        take: 0,
        name: "",
    });
    const { item } = route.params;
    useFocusEffect(
        useCallback(() => {
            async function fetchRelation(oppositeId) {
                const response = await getRelation(oppositeId);
                setData({
                    name: response.oppositeName,
                    give: response.totalSentAmount,
                    take: response.totalReceivedAmount,
                });
            }
            async function fetchTransaction(oppositeId) {
                const response = await getTransaction(oppositeId);
                setTakeData(
                    response.receive
                        .sort((a, b) => new Date(b.time) - new Date(a.time))
                        .map((item) => ({
                            transactionId: item.transactionId,
                            date: formatDate(item.time),
                            name: item.eventName,
                            tag: item.relationType,
                            amount: item.amount,
                        }))
                );
                setGiveData(
                    response.send
                        .sort((a, b) => new Date(b.time) - new Date(a.time))
                        .map((item) => ({
                            transactionId: item.transactionId,
                            date: formatDate(item.time),
                            name: item.eventName,
                            tag: item.relationType,
                            amount: item.amount,
                        }))
                );
            }
            fetchTransaction(item.oppositeId);
            fetchRelation(item.oppositeId);
        }, [])
    );
    useLayoutEffect(() => {
        navigation.setOptions({
            headerTitle: () => headerTitle(item.title), // 전달된 item.name이 없으면 기본 타이틀로 설정
        });
    }, [navigation, item]);
    function headerTitle(title) {
        return (
            <YoYoText type={"subTitle"} bold>
                {title || "일정 상세보기"}
            </YoYoText>
        );
    }
    function renderItem({ item }) {
        return <YoYoCardDetail data={item} />;
    }
    const detailList = () => {
        if (giveAndTake) {
            return (
                <FlatList
                    data={takeData}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.transactionId}
                ></FlatList>
            );
        } else {
            return (
                <FlatList
                    data={giveData}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.transactionId}
                ></FlatList>
            );
        }
    };
    async function clickButton() {
        const storedPayInfo = JSON.parse(await AsyncStorage.getItem("payInfo"))
        console.log(storedPayInfo)
        if (storedPayInfo.balance >= 0) {
            navigation.navigate("돈보내기", {
                title: "마음 전달",
                eventId: item.eventId,
            });
        } else {
            Alert.alert("페이 정보가 없습니다.", "YoYo페이를 등록해주세요.");
        }
    }

    return (
        <>
            <Container>
                <View style={styles.container}>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            장소
                        </YoYoText>
                        <YoYoText type={"md"}>{item.location}</YoYoText>
                    </View>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            일정
                        </YoYoText>
                        <YoYoText type={"md"}>
                            {item.startAt}
                            {" ~ "}
                            {item.endAt}
                        </YoYoText>
                    </View>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            {item.name}님과의 내역
                        </YoYoText>
                        <DetailMoneyGauge
                            give={data.give}
                            take={data.take}
                        ></DetailMoneyGauge>
                    </View>
                    <SelectTap
                        left={"받았어요"}
                        right={"보냈어요"}
                        leftColor={MainStyle.colors.main}
                        rightColor={MainStyle.colors.red}
                        stateHandler={setGiveAndTake}
                    />
                    {detailList()}
                </View>
            </Container>
            <Button type={"fill"} onPress={clickButton}>
                <YoYoText bold>마음 전하기</YoYoText>
            </Button>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        rowGap: 15,
    },
});