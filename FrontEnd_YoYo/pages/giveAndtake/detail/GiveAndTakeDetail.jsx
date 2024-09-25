import { View, StyleSheet, FlatList } from "react-native";
import React, { useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import Tag from "../../../components/common/Tag";
import Container from "../../../components/common/Container";
import DetailMoneyGauge from "../../../components/card/Yoyo/DetailMoneyGauge";
import SelectTap from "../../../components/common/SelectTap";
import YoYoCardDetail from "../../../components/card/Yoyo/YoYoCardDetail";
import { MainStyle } from "../../../constants/style";

export default function GiveAndTakeDetail({ route }) {
    const [giveAndTake, setGiveAndTake] = useState(true);
    const { id } = route.params;

    const DATA = [
        {
            id: "1",
            title: "First Item",
        },
        {
            id: "2",
            title: "Second Item",
        },
    ];

    const DATA2 = [
        {
            id: "1",
            title: "First Item",
        },
        {
            id: "2",
            title: "Second Item",
        },
        {
            id: "3",
            title: "third Item",
        },
    ];

    const detailList = () => {
        if (giveAndTake) {
            return (
                <FlatList
                    data={DATA}
                    renderItem={({}) => <YoYoCardDetail />}
                ></FlatList>
            );
        } else {
            return (
                <FlatList
                    data={DATA2}
                    renderItem={({}) => <YoYoCardDetail />}
                ></FlatList>
            );
        }
    };

    return (
        <Container>
            <View style={styles.outerContainer}>
                <View style={styles.innerContainer}>
                    <YoYoText type="title" bold>
                        김현태
                    </YoYoText>
                    <View style={styles.tagContainer}>
                        <Tag type="friend" width={88}>
                            친구
                        </Tag>
                    </View>
                </View>
                <View>
                    <YoYoText type="subTitle">고등학교 친구</YoYoText>
                </View>
                <DetailMoneyGauge give={54000} take={68000}></DetailMoneyGauge>
            </View>
            <View style={styles.selectContainer}>
                <SelectTap
                    stateHandler={setGiveAndTake}
                    leftColor={MainStyle.colors.main}
                    rightColor={MainStyle.colors.red}
                    left="받았어Yo"
                    right="보냈어Yo"
                />
            </View>
            {detailList()}
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
        alignItems: "center",
    },
    tagContainer: {
        marginLeft: 10,
    },
    selectContainer: {
        marginVertical: 10,
    },
});
