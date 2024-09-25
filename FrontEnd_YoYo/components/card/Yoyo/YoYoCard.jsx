import { View, Text, StyleSheet } from "react-native";
import React from "react";
import Card from "../Card";
import YoYoText from "../../../constants/YoYoText";
import Tag from "../../common/Tag";
import MoneyGauge from "./MoneyGauge";
import { CheckBox } from "../../common/CheckBox";

const tagTranslate = {
    all: "전체",
    friend: "친구",
    family: "가족",
    company: "직장",
    etc: "기타",
};

export default function YoYoCard({ data, type, onPress, selectedCard }) {
    return (
        <Card height={144} onPress={onPress}>
            <View style={styles.innerContainer}>
                <View>
                    <View
                        style={{
                            flexDirection: "row",
                            alignItems: "flex-end",
                            gap: 8,
                        }}
                    >
                        <YoYoText type="subTitle" bold>
                            {data.title}
                        </YoYoText>
                        {type === "select" && (
                            <CheckBox checked={data.id === selectedCard} />
                        )}
                    </View>
                    <YoYoText type="content">{data.description}</YoYoText>
                </View>
                <Tag type={data.type} width={88}>
                    {tagTranslate[data.type]}
                </Tag>
            </View>
            <MoneyGauge give={20000} take={12000} />
        </Card>
    );
}
const styles = StyleSheet.create({
    innerContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        width: "100%",
        alignItems: "flex-start",
    },
});
