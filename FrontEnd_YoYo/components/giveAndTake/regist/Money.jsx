import { View, Text, StyleSheet } from "react-native";
import React, { useEffect, useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import Input from "../../common/Input";
import ButtonList from "../../common/ButtonList";

export default function Money({ setIsActive }) {
    const [amount, setAmount] = useState(0);
    const [memo, setMemo] = useState("");
    const amountChange = (value) => {
        const numericValue = parseInt(value);
        setAmount(isNaN(numericValue) ? 0 : numericValue); // 숫자로 변환, 숫자가 아닐 경우 0으로 처리
    };
    useEffect(() => {
        if (amount > 0 || memo.length > 0) {
            setIsActive(true);
        }
    }, [amount, memo]);
    return (
        <View>
            <View style={styles.container}>
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        금액입력
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input
                        text={amount === 0 ? "" : String(amount)}
                        onChange={amountChange}
                        placeholder={"금액을 입력해주세요."}
                        type="phoneNumber"
                    />
                </View>
                <ButtonList size={76} setAmount={setAmount} />
            </View>
            <View style={styles.container}>
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        추가메모
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input
                        text={memo}
                        onChange={setMemo}
                        placeholder={"추가 메모를 기록해주세요."}
                    />
                </View>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        marginTop: 30,
    },
    textContainer: {
        marginTop: 30,
    },
});
