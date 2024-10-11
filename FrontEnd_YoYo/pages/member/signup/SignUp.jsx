import { View, StyleSheet, Alert } from "react-native";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import Container from "../../../components/common/Container";
import React, { useState, useEffect, useLayoutEffect } from "react";
import IconButton from "../../../components/common/IconButton";
import Next from "../../../components/common/Next";
import PhoneNumber from "../../../components/login/PhoneNumber";
import Password from "../../../components/login/Password";
import UserInfo from "../../../components/login/UserInfo";
import { signUp } from "../../../apis/https/member";

export default function SignUp({ navigation }) {
    const [stage, setStage] = useState(0);
    const [isActive, setIsActive] = useState(false);
    const [profile, setProfile] = useState({
        name: "",
        phoneNumber: "",
        password: "",
        birthDay: "",
        valid: false,
    });
    function clickNextButton() {
        if (isActive) {
            if (stage < 2) {
                setStage(stage + 1);
            } else {
                signUp(profile);
                Alert.alert("회원가입 완료", "회원가입이 완료되었습니다.", [
                    {
                        text: "확인",
                        onPress: () => navigation.navigate("Login"),
                    },
                ]);
            }
        }
        setIsActive(false);
    }

    function clickPrevButton() {
        if (stage === 0) {
            navigation.goBack();
        } else {
            setStage(stage - 1);
        }
    }

    useLayoutEffect(() => {
        navigation.setOptions({
            headerRight: () => (
                <Next
                    isActive={isActive}
                    onPress={clickNextButton}
                    final={stage === 2}
                />
            ),
            headerLeft: () => (
                <IconButton
                    icon="arrow-back-outline"
                    size={24}
                    onPress={clickPrevButton}
                />
            ),
        });
    }, [navigation, stage, isActive]);

    return (
        <Container>
            <View style={styles.titleContainer}>
                <YoYoText type="subTitle" bold color={MainStyle.colors.main}>
                    {stage === 0
                        ? "전화번호를 입력해주세요."
                        : stage === 1
                        ? "비밀번호를 입력해주세요."
                        : "개인정보를 입력해주세요."}
                </YoYoText>
            </View>
            <View>
                {stage === 0 && (
                    <PhoneNumber
                        profile={profile}
                        setProfile={setProfile}
                        setIsActive={setIsActive}
                    />
                )}
                {stage === 1 && (
                    <Password
                        setProfile={setProfile}
                        setIsActive={setIsActive}
                    />
                )}
                {stage === 2 && (
                    <UserInfo
                        profile={profile}
                        setProfile={setProfile}
                        setIsActive={setIsActive}
                    />
                )}
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    nextContainer: {
        flexDirection: "row-reverse",
    },
    titleContainer: {
        marginTop: 30,
    },
});
