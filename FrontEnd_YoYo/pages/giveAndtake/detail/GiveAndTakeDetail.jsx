import { View, StyleSheet, FlatList, ActivityIndicator } from "react-native";
import React, { useEffect, useState } from "react";
import { MainStyle } from "../../../constants/style";
import { getTransaction } from "../../../apis/https/transactionApi";
import { formatDate } from "../../../util/date";
import { getRelation } from "../../../apis/https/relationApi";
import YoYoText from "../../../constants/YoYoText";
import Tag from "../../../components/common/Tag";
import Container from "../../../components/common/Container";
import DetailMoneyGauge from "../../../components/card/Yoyo/DetailMoneyGauge";
import SelectTap from "../../../components/common/SelectTap";
import YoYoCardDetail from "../../../components/card/Yoyo/YoYoCardDetail";
import giveAndTakeLoader from "../../../components/loader/giveAndTakeLoader";

const tag = {
    all: "전체",
    family: "가족",
    friend: "친구",
    company: "직장",
    etc: "기타",
};

export default function GiveAndTakeDetail({ route }) {
    const [giveAndTake, setGiveAndTake] = useState(true);
    const [giveData, setGiveData] = useState([]);
    const [takeData, setTakeData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [data, setData] = useState({
        name: "",
        description: "",
        tag: "",
        give: 0,
        take: 0,
    });
    const { id } = route.params;
    useEffect(() => {
        async function fetchData() {
            try {
                const [relationResponse, transactionResponse] =
                    await Promise.all([getRelation(id), getTransaction(id)]);
                setData({
                    name: relationResponse.oppositeName,
                    description: relationResponse.description,
                    tag: relationResponse.relationType.toLowerCase(),
                    give: relationResponse.totalSentAmount,
                    take: relationResponse.totalReceivedAmount,
                });
                setTakeData(
                    transactionResponse.receive
                        .sort((a, b) => new Date(b.time) - new Date(a.time))
                        .map((item) => ({
                            transactionId: item.transactionId.toString(),
                            date: formatDate(item.time),
                            name: item.eventName,
                            tag: item.relationType,
                            amount: item.amount,
                        }))
                );
                setGiveData(
                    transactionResponse.send
                        .sort((a, b) => new Date(b.time) - new Date(a.time))
                        .map((item) => ({
                            transactionId: item.transactionId.toString(),
                            date: formatDate(item.time),
                            name: item.eventName,
                            tag: item.relationType,
                            amount: item.amount,
                        }))
                );
            } catch (error) {
                console.error("Error fetching data:", error);
            } finally {
                setIsLoading(false);
            }
        }

        fetchData();
    }, [id]);
    function renderItem({ item }) {
        return <YoYoCardDetail data={item} />;
    }
    const detailList = () => {
        if (giveAndTake) {
            if (takeData.length === 0) {
                return (
                    <View style={styles.resultContainer}>
                        <YoYoText type={"subTitle"} bold>
                            거래 내역이 없습니다.
                        </YoYoText>
                    </View>
                );
            } else {
                return (
                    <FlatList
                        data={takeData}
                        renderItem={renderItem}
                        keyExtractor={(item) => item.transactionId}
                    ></FlatList>
                );
            }
        } else {
            if (giveData.length === 0) {
                return (
                    <View style={styles.resultContainer}>
                        <YoYoText type={"subTitle"} bold>
                            거래 내역이 없습니다.
                        </YoYoText>
                    </View>
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
        }
    };
    return (
        <Container>
            <>
                {isLoading ? (
                    giveAndTakeLoader()
                ) : (
                    <View style={styles.outerContainer}>
                        <View style={styles.innerContainer}>
                            <YoYoText type="title" bold>
                                {data.name}
                            </YoYoText>
                            {data.tag && ( // data.tag가 존재할 때만 렌더링
                                <View style={styles.tagContainer}>
                                    <Tag type={data.tag} width={88}>
                                        {tag[data.tag]}
                                    </Tag>
                                </View>
                            )}
                        </View>
                        <View>
                            <YoYoText type="subTitle">
                                {data.description}
                            </YoYoText>
                        </View>
                        <DetailMoneyGauge give={data.give} take={data.take} />
                    </View>
                )}
            </>
            <View style={styles.selectContainer}>
                <SelectTap
                    stateHandler={setGiveAndTake}
                    leftColor={MainStyle.colors.main}
                    rightColor={MainStyle.colors.red}
                    left="받았어Yo"
                    right="보냈어Yo"
                />
            </View>
            {isLoading ? (
                <ActivityIndicator
                    style={styles.resultContainer}
                    size={((height = 100), (width = 100))}
                    color={MainStyle.colors.main}
                />
            ) : (
                detailList()
            )}
        </Container>
    );
}

const styles = StyleSheet.create({
    outerContainer: {
        width: "100%",
        rowGap: 10,
    },
    innerContainer: {
        flexDirection: "row",
        alignItems: "flex-end",
    },
    tagContainer: {
        marginLeft: 10,
    },
    selectContainer: {
        marginVertical: 20,
    },
    resultContainer: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
});
