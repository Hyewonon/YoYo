import { View, Text, StyleSheet } from "react-native";
import React from "react";
import YoYoText from "../../constants/YoYoText";
import IconButton from "../common/IconButton";
import { useNavigation } from "@react-navigation/native";

export default function Header() {
    const navigation = useNavigation();
    function clickNotification() {
        navigation.navigate("Notification");
    }

    return (
        <View style={styles.header}>
            <YoYoText type="mainTitle" logo>
                YoYo
            </YoYoText>
            <IconButton
                icon={"notifications-outline"}
                size={32}
                onPress={clickNotification}
            />
        </View>
    );
}

const styles = StyleSheet.create({
    header: {
        flexDirection: "row",
        marginVertical: 24,
        justifyContent: "space-between",
        alignItems: "center",
    },
});
