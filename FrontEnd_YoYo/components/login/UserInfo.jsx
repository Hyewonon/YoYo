import { View, StyleSheet } from "react-native";
import React, { useState, useEffect } from "react";
import Input from "../common/Input";
import validate from "../../util/validate";

export default function UserInfo({ profile, setProfile, setIsActive }) {

    useEffect(() => {
        if (profile.name.length > 0 && validate.validateName(profile.name) && profile.birthDay.length === 10) {
            setIsActive(true)
        }
    }, [profile]);
    return (
        <View style={styles.container}>
            <View style={styles.textContainer}>
                <Input
                    type="default"
                    placeholder="이름"
                    onChange={(text) => {
                        setProfile((prev) => ({
                            ...prev,
                            name: text
                        }))
                    }}
                    text={profile.name}
                    isError={profile.name.length > 0 && !validate.validateName(profile.name)}
                />
            </View>
            <View style={styles.textContainer}>
                <Input
                    type="phoneNumber"
                    placeholder="생년월일(ex.2024-12-31)"
                    onChange={(text) => {
                        setProfile((prev) => ({
                            ...prev,
                            birthDay: text
                        }))
                    }}
                    text={profile.birthDay}
                />
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
