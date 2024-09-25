import { View, StyleSheet, Image } from "react-native";
import React from "react";
import Container from "../../../components/common/Container";
import YoYoText from "../../../constants/YoYoText";
import Button from "../../../components/common/Button";

const bankImages = {
    KB국민은행: require("../../../assets/svg/banks/KB국민은행.png"),
    신한은행: require("../../../assets/svg/banks/신한은행.png"),
    하나은행: require("../../../assets/svg/banks/하나은행.png"),
    IBK기업은행: require("../../../assets/svg/banks/IBK기업은행.png"),
    카카오뱅크: require("../../../assets/svg/banks/카카오뱅크.png"),
    NH농협은행: require("../../../assets/svg/banks/NH농협은행.png"),
    SC제일은행: require("../../../assets/svg/banks/SC제일은행.png"),
    토스뱅크: require("../../../assets/svg/banks/토스뱅크.png"),
    우리은행: require("../../../assets/svg/banks/우리은행.png"),
};

export default function ManageAccount({ navigation }) {
    const data = {
        name: "KB국민은행",
        accountNumber: "123456-12-123456",
    };
    function clickButton() {
        navigation.navigate("계좌등록");
    }
    return (
        <Container>
            <View>
                <View>
                    <YoYoText type={"title"} bold>
                        나의 계좌
                    </YoYoText>
                </View>
                <View style={styles.bankContainer}>
                    <View style={styles.imageContainer}>
                        <Image source={bankImages[data.name]} />
                    </View>
                    <View style={styles.textContainer}>
                        <YoYoText type={"md"} bold>
                            {data.name}
                        </YoYoText>
                        <YoYoText type={"desc"}>{data.accountNumber}</YoYoText>
                    </View>
                </View>
                <View>
                    <Button type={"fill"} radius={16} onPress={clickButton}>
                        <YoYoText type={"md"} bold>
                            계좌 변경하기
                        </YoYoText>
                    </Button>
                </View>
            </View>
        </Container>
    );
}
const styles = StyleSheet.create({
    bankContainer: {
        flexDirection: "row",
        paddingVertical: 16,
        alignItems: "center",
    },
    imageContainer: { padding: 16 },
    textContainer: { padding: 16 },
    buttonContainer: { borderRadius: 24 },
});
