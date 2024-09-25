import { Text } from "react-native";
import React from "react";
import useFontsLoader from "./useFontsLoader";
import { MainStyle } from "./style";

export default function YoYoText({
    type,
    color,
    center,
    bold,
    children,
    logo,
}) {
    const fontsLoaded = useFontsLoader();
    if (!fontsLoaded) return null;

    let fontStyle = {
        title: {
            fontSize: 32,
            color: color,
        },
        subTitle: {
            fontSize: 24,
            color: color,
        },
        content: {
            fontSize: 14,
            color: color,
        },
        subContent: {
            fontSize: 13,
            color: color,
        },
        md: {
            fontSize: 18,
            color: color,
        },
        desc: {
            fontSize: 15,
            color: color,
        },
        xs: {
            fontSize: 10,
            color: color,
        },
        xxs: {
            fontSize: 8,
            color: color,
        },
        mainTitle: {
            fontSize: 48,
            color: color,
        },
    };
    let finalStyle = {
        ...fontStyle[type],
        fontWeight: bold ? "bold" : "normal",
        textAlign: center ? "center" : "left",
    };
    if (logo) finalStyle = {
        ...fontStyle[type],
        fontWeight: bold ? "bold" : "normal",
        textAlign: center ? "center" : "left",
        fontFamily: MainStyle.fontFamily.OleoBold
    };

    return <Text style={finalStyle}>{children}</Text>;
}
