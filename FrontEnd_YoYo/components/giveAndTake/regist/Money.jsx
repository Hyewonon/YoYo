import { View, Text, StyleSheet } from "react-native";
import React, { useEffect, useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import Input from "../../common/Input";
import ButtonList from "../../common/ButtonList";

export default function Money({ setIsActive, person, setPerson }) {
    const amountChange = (value) => {
        const numericValue = parseInt(value);
        setPerson((prevPerson) => ({
            ...prevPerson,
            amount: isNaN(numericValue) ? 0 : numericValue,
        }));
    };

    const memoChange = (value) => {
        setPerson((prevPerson) => ({
            ...prevPerson,
            memo: value,
        }));
    };

    useEffect(() => {
        if (person.amount > 0 || person.memo.length > 0) {
            setIsActive(true);
        }
    }, [person.amount, person.memo]);

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
                        text={person.amount === 0 ? "" : String(person.amount)}
                        onChange={amountChange}
                        placeholder={"금액을 입력해주세요."}
                        type="phoneNumber"
                    />
                </View>
                <ButtonList
                    size={76}
                    setAmount={(increment) =>
                        setPerson((prevPerson) => ({
                            ...prevPerson,
                            amount: prevPerson.amount + increment,
                        }))
                    }
                />
            </View>
            <View style={styles.container}>
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        추가메모
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input
                        text={person.memo}
                        onChange={memoChange}
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
