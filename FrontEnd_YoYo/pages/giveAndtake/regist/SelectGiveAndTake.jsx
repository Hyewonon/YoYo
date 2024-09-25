import { View, StyleSheet } from "react-native";
import React from "react";
import Container from "../../../components/common/Container";
import WhiteButton from "../../../components/common/WhiteButton";
import { FontAwesome6 } from "@expo/vector-icons";
import { MainStyle } from "../../../constants/style";
import { useNavigation } from "@react-navigation/native";

export default function SelectButton() {
    const navigation = useNavigation();

    function clickTakeButton() {
        navigation.navigate("GiveAndTakeRegist", { type: 1 });
    }

    function clickGiveButton() {
        navigation.navigate("GiveAndTakeRegist", { type: 2 });
    }

    return (
        <Container>
            <View style={styles.buttonContainer}>
                <WhiteButton text={"받았어Yo"} onPress={clickTakeButton}>
                    <FontAwesome6
                        name={"gift"}
                        size={76}
                        color={MainStyle.colors.main}
                    />
                </WhiteButton>
                <WhiteButton text={"보냈어Yo"} onPress={clickGiveButton}>
                    <FontAwesome6
                        name={"gift"}
                        size={76}
                        color={MainStyle.colors.red}
                    />
                </WhiteButton>
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    buttonContainer: {
        flex: 1,
        flexDirection: "row",
        justifyContent: "space-around",
        alignItems: "center",
    },
});
