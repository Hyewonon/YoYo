import { View, StyleSheet } from "react-native";
import React from "react";
import YoYoText from "../../../constants/YoYoText";
import IconButton from "../../common/IconButton";
import { MainStyle } from "../../../constants/style";
import format from "../../../util/format";
import { deleteTransaction } from "../../../apis/https/transactionApi";

export default function YoYoCardDetail({ data, type, onPress }) {
    return (
        <View style={styles.cardContainer}>
            <View style={styles.innerContainer}>
                <YoYoText type="subTitle" bold>
                    {data.name}
                </YoYoText>
            </View>
            <View style={styles.innerContainer2}>
                <YoYoText type="content">{data.date}</YoYoText>
                <View
                    style={{
                        alignItems: "flex-end",
                    }}
                >
                    <YoYoText type="subTitle">
                        {format.formatNumber(data.amount)}원
                    </YoYoText>
                </View>
            </View>
        </View>
    );
}
const styles = StyleSheet.create({
    cardContainer: {
        borderRadius: 16,
        overflow: "hidden",
        marginBottom: 24,
        borderWidth: 2,
        borderColor: MainStyle.colors.main,
        padding: 12,
    },
    innerContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        width: "100%",
        alignItems: "flex-start",
    },
    innerContainer2: {
        flexDirection: "row",
        justifyContent: "space-between",
        width: "100%",
        alignItems: "flex-end",
    },
});
