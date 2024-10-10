import { View, StyleSheet } from "react-native";
import React, { useEffect, useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import { MainStyle } from "../../../constants/style";
import Input from "../../common/Input";
import validate from "../../../util/validate";

export default function Name({ setIsActive, setPerson }) {
    const [name, setName] = useState("");

    useEffect(() => {
        if (name.length > 0) {
            setIsActive(validate.validateName(name));
            setPerson((prevPerson) => ({
                ...prevPerson,
                name: name,
            }));
        }
    }, [name]);

    return (
        <View style={styles.titleContainer}>
            <View>
                <YoYoText type="title" bold color={MainStyle.colors.main}>
                    이름 입력
                </YoYoText>
            </View>
            <View style={styles.textContainer}>
                <Input
                    placeholder={"이름을 입력하세요."}
                    onChange={setName}
                    text={name}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    titleContainer: {
        marginTop: 30,
    },
    textContainer: {
        marginTop: 30,
    },
});
