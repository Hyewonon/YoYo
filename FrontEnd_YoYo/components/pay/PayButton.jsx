import { View, Image, StyleSheet, Pressable } from "react-native";
import React from "react";
import YoYoText from "../../constants/YoYoText";
const buttonImages = {
    Migration: require("../../assets/svg/button/Migration.png"),
    Charge: require("../../assets/svg/button/Charge.png"),
    PayList: require("../../assets/svg/button/PayList.png"),
    MyAccount: require("../../assets/svg/button/MyAccount.png"),
};
export default function BankButton({ data, onPress }) {
    return (
        <View style={styles.outerContainer}>
            <Pressable
                android_ripple={{ color: "#ccc" }}
                style={styles.container}
                onPress={onPress}
            >
                {data.mode === 1 ? (
                    <View style={styles.modeOneContainer}>
                        <View>
                            <YoYoText type="subTitle" bold>
                                {data.title}
                            </YoYoText>
                        </View>
                        <View style={styles.modeOneSubContainer}>
                            <Image
                                source={buttonImages[data.type]}
                                style={[data.style]}
                            />
                            <View>
                                <YoYoText type="desc">{data.text}</YoYoText>
                            </View>
                        </View>
                    </View>
                ) : (
                    <View style={styles.modeTwoContainer}>
                        <View style={styles.textContainer}>
                            <View>
                                <YoYoText type="subTitle" bold>
                                    {data.title}
                                </YoYoText>
                            </View>
                            <View>
                                <YoYoText type="desc">{data.text}</YoYoText>
                            </View>
                        </View>

                        <View>
                            <Image
                                source={buttonImages[data.type]}
                                style={[data.style]}
                            />
                        </View>
                    </View>
                )}
            </Pressable>
        </View>
    );
}
const styles = StyleSheet.create({
    outerContainer: {
        overflow: "hidden",
        borderRadius: 16,
        elevation: 6,
        backgroundColor: "white",
    },
    container: {
        justifyContent: "center",
        borderRadius: 16,
        padding: 8,
    },
    modeOneSubContainer: {
        alignItems: "center",
    },
    modeOneContainer: {
        paddingVertical: 8,
    },
    modeTwoContainer: {
        paddingVertical: 8,
        flexDirection: "row",
        justifyContent: "space-around",
    },
    textContainer: { rowGap: 10 },
});
