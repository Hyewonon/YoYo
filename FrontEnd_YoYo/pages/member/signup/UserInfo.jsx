import { View, Text, Pressable, StyleSheet } from "react-native";
import React from "react";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../../components/common/Input";
import IconButton from "../../../components/common/IconButton";
import { MainStyle } from "../../../constants/style";
import { useNavigation } from "@react-navigation/native";
import Container from "../../../components/common/Container";
import { useState } from "react";

export default function UserInfo() {
    const navigation = useNavigation();
    const [name, setName] = useState("");
    const [birthday, setBirthday] = useState("");
    const [address, setAddress] = useState("");

    const clickNextHandler = () => {
        navigation.navigate("Login");
    };
    return (
        <Container>
            <View style={styles.nextContainer}>
                <Pressable onPress={clickNextHandler}>
                    <YoYoText type="subTitle" bold>
                        완료
                    </YoYoText>
                </Pressable>
            </View>
            <View style={styles.titleContainer}>
                <YoYoText type="subTitle" bold color={MainStyle.colors.main}>
                    개인정보를 입력해주세요.
                </YoYoText>
            </View>
            <View style={styles.textContainer}>
                <Input
                    type="default"
                    placeholder="이름"
                    onChange={setName}
                    text={name}
                />
            </View>
            <View style={styles.textContainer}>
                <Input
                    type="phoneNumber"
                    placeholder="생년월일"
                    onChange={setBirthday}
                    text={birthday}
                />
            </View>
            <View style={styles.outerContainer}>
                <View style={styles.inputContainer}>
                    <Input
                        type="default"
                        placeholder="주소"
                        onChange={setAddress}
                        text={address}
                    />
                </View>
                <View style={styles.buttonContainer}>
                    <IconButton icon="search" size={25} />
                </View>
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
    outerContainer: {
        flexDirection: "row",
        marginVertical: 5,
    },
    inputContainer: {
        flex: 6,
    },
    buttonContainer: {
        padding: 10,
        flex: 0.5,
    },
});
