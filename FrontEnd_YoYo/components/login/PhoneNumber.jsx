import { View, StyleSheet, Alert } from "react-native";
import React from "react";
import Input from "../common/Input";
import Button from "../common/Button";
import YoYoText from "../../constants/YoYoText";
import { useEffect, useState } from "react";
import { checkMessage, sendMessage } from "../../apis/https/member";

export default function PhoneNumber({ profile, setProfile, setIsActive }) {

    const [authNumber, setAuthNumber] = useState("");
    const [isComplete, setIsComplete] = useState(false);

    const sendAuthMessageHandler = async () => {
        if (profile.phoneNumber.length === 11 && !isComplete) {
            const response = await sendMessage(profile.phoneNumber);
            if (!response) {
                Alert.alert("전화번호 입력 실패", "전화번호를 확인해주세요.")
            } else {
                Alert.alert("", "인증번호가 발송되었습니다.")
                setIsComplete(true)
            }
        } else return
    }
    const clickAuthHandler = async () => {
        if (authNumber.length === 6 && isComplete) {
            const response = await checkMessage(profile.phoneNumber, authNumber)
            if (response) {
                Alert.alert("", "인증번호가 확인되었습니다.")
                setIsActive(true)
                setIsComplete(false)
                setProfile((prev) => ({
                    ...prev,
                    valid: true
                }))
            } else {
                Alert.alert("", "인증번호를 확인해주세요.")
            }
        } else
            return
    };

    return (
        <View style={styles.container}>
            <View style={styles.outerContainer}>
                <View style={styles.textContainer}>
                    <Input
                        type="phoneNumber"
                        onChange={(text) => {
                            setProfile((prev) => ({
                                ...prev,
                                phoneNumber: text
                            }))
                        }}
                        placeholder={"ex) 01012345678"}
                        text={profile.phoneNumber}
                    />
                </View>
                <View style={styles.buttonContainer}>
                    <Button type={profile.phoneNumber.length === 11 && authNumber.length < 6 && !isComplete ? "normal" : "inactive"} radius={24} onPress={sendAuthMessageHandler}>
                        <YoYoText type="content" bold>
                            인증번호받기
                        </YoYoText>
                    </Button>
                </View>
            </View>
            <View style={styles.outerContainer}>
                <View style={styles.textContainer}>
                    <Input
                        type="phoneNumber"
                        onChange={setAuthNumber}
                        placeholder={"인증번호 6자리"}
                        text={authNumber}
                    />
                </View>
                <View style={styles.buttonContainer}>
                    <Button
                        type={authNumber.length === 6 && isComplete ? "normal" : "inactive"}
                        radius={24}
                        onPress={clickAuthHandler}
                    >
                        <YoYoText type="content" bold>
                            인증
                        </YoYoText>
                    </Button>
                </View>
            </View>
        </View >
    );
}

const styles = StyleSheet.create({
    container: {
        marginTop: 30,
    },
    outerContainer: {
        flexDirection: "row",
        marginVertical: 5,
    },
    textContainer: {
        flex: 3,
    },
    buttonContainer: {
        flex: 1.5,
    },
});
