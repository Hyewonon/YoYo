import { View, StyleSheet } from "react-native";
import React, { useEffect } from "react";

import { useState } from "react";
import YoYoText from "../../constants/YoYoText";
import Input from "../common/Input";

export default function Password({ setProfile, setIsActive }) {
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
            setIsSame(false);
        }
        if (isSame) {
            setProfile((prev) => ({
                ...prev,
                password: rePassword
            }))
        } else {
            setProfile((prev) => ({
                ...prev,
                password: ""
            }))
        }
        if (
            password.length > 0 &&
            rePassword.length > 0 &&
            isSame &&
            !isError
        ) {
            setIsActive(true);
        } else {
            setIsActive(false);
        }
    }, [password, rePassword, isSame, isError]);

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

    return (
        <View>
            <View>
                <YoYoText bold>{"•"} 8 ~ 16자리로 입력해주세요.</YoYoText>
                <YoYoText bold>
                    {"•"} 숫자, 영문자(대/소문자), 특수문자(@,$,!,%,*,?,&)를
                    포함하여 주세요.
                </YoYoText>
            </View>
            <View style={styles.container}>
                <View style={styles.textContainer}>
                    <Input
                        type="password"
                        placeholder={"비밀번호 입력"}
                        onChange={setPassword}
                        text={password}
                        isError={isError}
                        errorMessage="비밀번호 형식이 올바르지 않습니다."
                    />
                </View>
                <View style={styles.textContainer}>
                    <Input
                        type="password"
                        placeholder={"비밀번호 확인"}
                        onChange={setRePassword}
                        text={rePassword}
                        isError={rePassword.length > 0 && !isSame}
                        errorMessage="비밀번호를 확인해주세요." /
                    >
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
        marginVertical: 5,
    },
});
