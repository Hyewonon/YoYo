import { StyleSheet, Text, View, Pressable } from "react-native";
import React from "react";
import { useState } from "react";
import { MainStyle } from "../../constants/style";

export default function Button({ type, children, width, onPress }) {
    let buttonStyle = {
        normal: {
            backgroundColor: MainStyle.colors.white,
            borderColor: MainStyle.colors.main,
        },
        fill: {
            backgroundColor: MainStyle.colors.main,
            borderColor: MainStyle.colors.main,
        },
        inactive: {
            backgroundColor: MainStyle.colors.gray,
            borderColor: MainStyle.colors.gray,
        },
    };

    let textStyle = {
        normal: { color: MainStyle.colors.main },
        fill: {
            color: MainStyle.colors.white,
        },
        inactive: {
            color: MainStyle.colors.white,
        },
    };

    return (
        <View style={[styles.buttonOuterContainer, { width: width }]}>
            <Pressable
                style={({ pressed }) => [
                    styles.buttonInnerContainer,
                    buttonStyle[type],
                    pressed && styles.pressed,
                ]}
                onPress={onPress}
            >
                <Text style={[{ ...textStyle[type] }]}>{children}</Text>
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    buttonOuterContainer: {
        margin: 4,
        overflow: "hidden",
    },
    buttonInnerContainer: {
        alignItems: "center",
        justifyContent: "center",
        paddingVertical: 12,
        borderRadius: 24,
        borderWidth: 2,
    },
    normalText: {
        fontSize: MainStyle.fontSize.subTitle,
        fontWeight: "bold",
    },
    pressed: {
        opacity: 0.75,
    },
});
