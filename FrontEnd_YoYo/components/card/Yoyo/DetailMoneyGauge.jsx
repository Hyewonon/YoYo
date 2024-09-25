import { View, StyleSheet } from "react-native";
import React from "react";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import format from "../../../util/format";

export default function DetailMoneyGauge({ give, take }) {
    // 게이지 너비 give & take 비율로 결정
    const gaugeWidth = (take / (give + take)) * 100 + "%";

    return (
        <View>
            <View style={styles.baseGuage}>
                <View style={[styles.mainGuage, { width: gaugeWidth }]} />
            </View>
            <View style={styles.rowContainer}>
                <YoYoText type="subTitle" bold color={MainStyle.colors.main}>
                    {format.formatNumber(take)}
                </YoYoText>
                <YoYoText type="subTitle" bold color={MainStyle.colors.red}>
                    {format.formatNumber(give)}
                </YoYoText>
            </View>
        </View>
    );
}
const styles = StyleSheet.create({
    rowContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
    },
    baseGuage: {
        width: "100%",
        backgroundColor: MainStyle.colors.lightGray,
        height: 12,
        borderRadius: 16,
        position: "relative",
        marginTop: 4,
    },
    mainGuage: {
        backgroundColor: MainStyle.colors.main,
        height: 12,
        borderRadius: 16,
        position: "absolute",
        left: 0,
        top: 0,
    },
});
