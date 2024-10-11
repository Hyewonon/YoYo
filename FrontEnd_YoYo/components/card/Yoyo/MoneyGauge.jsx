import { View, StyleSheet } from "react-native";
import React from "react";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import format from "../../../util/format";

export default function MoneyGauge({ give, take }) {
    const gaugeWidth = (take / (give + take)) * 100 + "%";
    return (
        <View>
            <View style={styles.rowContainer}>
                <YoYoText type="xs" bold>
                    받았어Yo
                </YoYoText>
                <YoYoText type="xs" bold>
                    보냈어Yo
                </YoYoText>
            </View>
            <View style={styles.baseGuage}>
                <View style={[styles.mainGuage, { width: gaugeWidth }]} />
            </View>
            <View style={styles.rowContainer2}>
                <YoYoText type="content" bold>
                    {format.formatNumber(take)}원
                </YoYoText>
                <YoYoText type="content" bold>
                    {format.formatNumber(give)}원
                </YoYoText>
            </View>
        </View>
    );
}
const styles = StyleSheet.create({
    rowContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        marginTop: 12,
    },
    rowContainer2: {
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
