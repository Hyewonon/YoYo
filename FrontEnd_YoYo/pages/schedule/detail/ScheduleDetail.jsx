import { View, Text, StyleSheet } from "react-native";
import React, { useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import Container from "../../../components/common/Container";
import DetailMoneyGauge from "../../../components/card/Yoyo/DetailMoneyGauge";
import SelectTap from "../../../components/common/SelectTap";
import YoYoCardDetail from "../../../components/card/Yoyo/YoYoCardDetail";
import { FlatList } from "react-native-gesture-handler";
import { MainStyle } from "../../../constants/style";
import Button from "../../../components/common/Button";

export default function ScheduleDetail({ navigation }) {
    const [giveAndTake, setGiveAndTake] = useState(true);

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
    function clickButton() {
        navigation.navigate("돈보내기", {
            title: "마음 전달",
        });
    }

    return (
        <>
            <Container>
                <View style={styles.container}>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            장소
                        </YoYoText>
                        <YoYoText type={"md"}>장소에 대한 정보가 기재</YoYoText>
                    </View>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            일정
                        </YoYoText>
                        <YoYoText type={"md"}>일정에 대한 정보가 기재</YoYoText>
                    </View>
                    <View>
                        <YoYoText type={"subTitle"} bold>
                            주최자와의 내역
                        </YoYoText>
                        <DetailMoneyGauge
                            give={54000}
                            take={68000}
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
