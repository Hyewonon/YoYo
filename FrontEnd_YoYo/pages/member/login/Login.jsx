import React, { useState } from "react";
import { View, Text, StyleSheet, Pressable, Alert } from "react-native";
import { MainStyle } from "../../../constants/style";
import YoYoText from "../../../constants/YoYoText";
import LoginBackground from "../../../assets/svg/loginBackground.svg";
import LoginYoYo from "../../../assets/svg/loginYoYo.svg";
import Button from "../../../components/common/Button";
import Input from "../../../components/common/Input";
import { useNavigation } from "@react-navigation/native";
import useFontsLoader from "../../../constants/useFontsLoader";
import { login as loginApi, savePushToken } from "../../../apis/https/member";
import { useDispatch } from "react-redux";
import { login } from "../../../store/slices/authSlice";
import * as Notifications from "expo-notifications"
import Constants from 'expo-constants';
export default function Login() {
    const dispatch = useDispatch()
    const navigation = useNavigation();
    const [phoneNumber, setPhoneNumber] = useState("");
    const [password, setPassword] = useState("");
    const clickSignUpHandler = () => {
        navigation.navigate("SignUp");
    };

    async function getPushToken() {
        try {
            const projectId = Constants.expoConfig.extra.eas.projectId;
            const token = (await Notifications.getExpoPushTokenAsync({ projectId })).data;
            return token;
        } catch (error) {
            console.error("Error getting FCM token: ", error);
            Alert.alert("푸시 알림 토큰을 가져오는 데 실패했습니다.",);
            return null;
        }
    }
    async function requestPushToken() {
        const { status } = await Notifications.getPermissionsAsync();
        if (status !== "granted") {
            const { status: newStatus } =
                await Notifications.requestPermissionsAsync();
            if (newStatus !== "granted") {
                alert("Push notification permissions required!");
                return;
            }
        }
        return await getPushToken();
    }
    const clickLoginHandler = async () => {
        // 로그인 버튼 클릭 시 처리할 로직
        try {
            const response = await loginApi(phoneNumber, password);
            const token = response.jwtToken
            const memberId = response.memberId
            // 푸시 알림 수신 핸들러 설정
            Notifications.setNotificationHandler({
                handleNotification: async () => ({
                    shouldShowAlert: true,
                    shouldPlaySound: true,
                    shouldSetBadge: true,
                }),
            });
            const pushToken = await requestPushToken();




            dispatch(login({ token, memberId, pushToken }))
            console.log(pushToken)
            await savePushToken(pushToken);

        } catch (error) {
            Alert.alert(
                "로그인 실패",
                "전화번호 또는 비밀번호를 확인해주세요."
            );
        }
    };
    const fontsLoaded = useFontsLoader();
    if (!fontsLoaded) return null;

    return (
        <View
            // 배경 이미지 경로
            style={styles.background}
        >
            <LoginBackground />

            <View style={styles.outerContainer}>
                <View style={styles.logoContainer}>
                    {/* 상단 텍스트 */}
                    <Text style={styles.logoText}>Yo! Yo!</Text>
                </View>

                <View style={styles.inputContainer}>
                    {/* 전화번호 입력 */}
                    <Input
                        type="phoneNumber"
                        placeholder="전화번호를 입력해주세요."
                        onChange={setPhoneNumber}
                        isError={false}
                        text={phoneNumber}
                    />

                    {/* 비밀번호 입력 */}
                    <Input
                        type="password"
                        placeholder="비밀번호를 입력해주세요."
                        onChange={setPassword}
                        isError={false}
                        text={password}
                    />

                    {/* 로그인 버튼 */}
                    <Button
                        type="fill"
                        width="100%"
                        onPress={clickLoginHandler}
                        radius={24}
                    >
                        <YoYoText type={"content"} bold>
                            로그인
                        </YoYoText>
                    </Button>

                    <View style={styles.textContainer}>
                        <YoYoText type={"content"}>
                            아직 회원이 아니신가요?
                        </YoYoText>
                        <Pressable onPress={clickSignUpHandler}>
                            <YoYoText
                                type={"content"}
                                color={MainStyle.colors.main}
                                bold
                            >
                                {"  "}
                                회원가입
                            </YoYoText>
                        </Pressable>
                    </View>
                </View>
            </View>
            <View style={styles.imageContainer}>
                <LoginYoYo />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    background: {
        flex: 1,
        backgroundColor: MainStyle.colors.hover,
    },
    outerContainer: {
        position: "absolute",
        top: 150,
        width: "100%",
        alignItems: "center",
        borderRadius: 10,
    },
    logoContainer: {
        marginBottom: 70,
    },
    inputContainer: {
        alignItems: "center",
        width: "80%",
        rowGap: 10,
    },
    imageContainer: {
        width: "100%",
        position: "absolute",
        bottom: 0,
    },
    textContainer: {
        flexDirection: "row",
    },
    logoText: {
        fontSize: MainStyle.fontSize.logo,
        color: MainStyle.colors.main,
        fontFamily: MainStyle.fontFamily.OleoBold,
    },
});
