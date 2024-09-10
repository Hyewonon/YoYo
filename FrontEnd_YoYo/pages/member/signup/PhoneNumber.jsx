import { View, Pressable, StyleSheet } from "react-native";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../../components/common/Input";
import Button from "../../../components/common/Button";
import { MainStyle } from "../../../constants/style";
import { useNavigation } from "@react-navigation/native";
import Container from "../../../components/common/Container";
import { React, useState } from "react";

export default function SignUp() {
    const navigation = useNavigation();
    const [phoneNumber, setPhoneNumber] = useState("");
    const [authNumber, setAuthNumber] = useState("");
    const [isComplete, setIsComplete] = useState(false);

    const clickNextHandler = () => {
        if (isComplete === true) {
            navigation.navigate("Password");
        }
    };

    const clickAuthHandler = () => {
        setIsComplete(true);
    };

    return (
        <Container>
            <View style={styles.nextContainer}>
                <Pressable onPress={clickNextHandler}>
                    <YoYoText
                        type="subTitle"
                        bold
                        color={
                            isComplete
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
                    전화번호를 입력해주세요.
                </YoYoText>
            </View>
            <View style={styles.outerContainer}>
                <View style={styles.textContainer}>
                    <Input
                        type="phoneNumber"
                        onChange={setPhoneNumber}
                        placeholder={"ex) 01012345678"}
                        text={phoneNumber}
                    ></Input>
                </View>
                <View style={styles.buttonContainer}>
                    <Button type="normal">
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
                        isError={true}
                    ></Input>
                </View>
                <View style={styles.buttonContainer}>
                    <Button type="normal" onPress={clickAuthHandler}>
                        <YoYoText type="content" bold>
                            인증
                        </YoYoText>
                    </Button>
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
