import { View, Text, StyleSheet, Alert } from "react-native";
import React, { useLayoutEffect, useState } from "react";
import Container from "../../../components/common/Container";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../../components/common/Input";
import Button from "../../../components/common/Button";
import { MainStyle } from "../../../constants/style";
import AsyncStorage from "@react-native-async-storage/async-storage";
const prices = [5000, 10000, 50000, 100000];
export default function SendMoney({ route, navigation }) {
    const [price, setPrice] = useState(0);
    const eventId = route.params.eventId;

    const title = route.params.title || "마음 전달";
    useLayoutEffect(() => {
        navigation.setOptions({
            title: title,
        });
    }, [navigation, route.params]);
    function handlePriceChange(text) {
        const numericValue = parseInt(text.replace(/[^0-9]/g, ""), 10) || 0;
        setPrice(numericValue);
    }
    async function clickNextHandler() {
        const storedPayInfo = JSON.parse(await AsyncStorage.getItem("payInfo"))

        if (storedPayInfo.balance >= 0) {
            navigation.navigate("RegistPayPassword", {
                data: {
                    title: `${title} 완료`,
                    content: `${price}원이 ${title === "충전하기" ? "충전되었습니다" : "송금되었습니다"
                        }.`,
                    money: price,
                },
                eventId: eventId ? eventId : "",
                type: "pay",
            });
        } else {
            Alert.alert("페이 정보가 없습니다.", "YoYo페이를 등록해주세요.")
        }
    }
    return (
        <>
            <Container>
                <YoYoText type="md" bold>
                    금액을 입력해주세요.
                </YoYoText>
                <Input
                    onChange={handlePriceChange}
                    text={price > 0 && price.toString()}
                    placeholder="금액을 입력해주세요."
                    type="phoneNumber"
                />
                {title === "마음 전달" && <YoYoText type="content" color={MainStyle.colors.error} bold>※부족한 금액은 자동 충전됩니다.</YoYoText>}
                <View style={styles.buttonContainer}>
                    {prices.map((money) => (
                        <Button
                            type="normal"
                            width="22%"
                            radius={24}
                            key={money}
                            onPress={() => setPrice(price + money)}
                        >
                            +{money}
                        </Button>
                    ))}
                </View>
            </Container>
            <Button
                radius={0}
                width="100%"
                type={price > 0 ? "fill" : "inactive"}
                onPress={clickNextHandler}
            >
                <YoYoText type="md" bold>
                    확인
                </YoYoText>
            </Button>
        </>
    );
}
const styles = StyleSheet.create({
    buttonContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        marginTop: 24,
    },
});
