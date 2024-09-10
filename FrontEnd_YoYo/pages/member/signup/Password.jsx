import { View, Text, StyleSheet, Pressable } from "react-native";
import React, { useEffect } from "react";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../../components/common/Input";

import { useNavigation } from "@react-navigation/native";
import Container from "../../../components/common/Container";
import { MainStyle } from "../../../constants/style";
import { useState } from "react";

export default function Password() {
    const navigation = useNavigation();
    const [password, setPassword] = useState("");
    const [rePassword, setRePassword] = useState("");
    const [isError, setIsError] = useState(false);
    const [isSame, setIsSame] = useState(true);

    useEffect(() => {
        if (password.length > 0) {
            setIsError(!validatePassword());
        } else {
            setIsError(false);
        }
        if (rePassword.length > 0) {
            setIsSame(identifyPassword());
        } else {
            setIsSame(true);
        }
    }, [password, rePassword]);

    const identifyPassword = () => {
        return (
            password.length > 0 &&
            rePassword.length > 0 &&
            password === rePassword
        );
    };

    const validatePassword = () => {
        const regex =
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
        return password.length > 0 && regex.test(password);
    };

    const clickNextHandler = () => {
        if (isSame && !isError) navigation.navigate("UserInfo");
    };
    return (
        <Container>
            <View style={styles.nextContainer}>
                <Pressable onPress={clickNextHandler}>
                    <YoYoText
                        type="subTitle"
                        bold
                        color={
                            identifyPassword()
                                ? MainStyle.colors.black
                                : MainStyle.colors.gray
                        }
                    >
                        다음
                    </YoYoText>
                </Pressable>
            </View>
            <View style={styles.titleContainer}>
                <YoYoText type="subTitle" bold color={MainStyle.colors.main}>
                    비밀번호를 입력해주세요.
                </YoYoText>
                <YoYoText bold>{"•"} 8 ~ 16자리로 입력해주세요.</YoYoText>
                <YoYoText bold>
                    {"•"} 숫자, 영문자(대/소문자), 특수문자(@,$,!,%,*,?,&)를
                    포함하여 주세요.
                </YoYoText>
            </View>
            <View style={styles.textContainer}>
                <Input
                    type="password"
                    placeholder={"비밀번호 입력"}
                    onChange={setPassword}
                    text={password}
                    isError={isError}
                ></Input>
            </View>
            <View style={styles.textContainer}>
                <Input
                    type="password"
                    placeholder={"비밀번호 확인"}
                    onChange={setRePassword}
                    text={rePassword}
                    isError={!isSame}
                ></Input>
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    nextContainer: {
        flexDirection: "row-reverse",
    },
    titleContainer: {
        marginVertical: 30,
    },
    textContainer: {
        marginVertical: 5,
    },
});
