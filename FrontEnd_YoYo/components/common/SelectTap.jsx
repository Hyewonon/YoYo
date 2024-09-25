import { View, Text, Pressable, StyleSheet } from "react-native";
import { useState } from "react";
import React from "react";
import YoYoText from "../../constants/YoYoText";
import { MainStyle } from "../../constants/style";

export default function SelectTap({
    left,
    right,
    leftColor,
    rightColor,
    stateHandler,
}) {
    const [isTakeSelected, setIsTakeSelected] = useState(true);

    const buttonTakeHandler = () => {
        setIsTakeSelected(true);
        stateHandler(true);
    };

    const buttonGiveHandler = () => {
        setIsTakeSelected(false);
        stateHandler(false);
    };

    return (
        <View style={styles.container}>
            {/* '받았어요' (Take) */}
            <Pressable
                onPress={buttonTakeHandler}
                style={styles.buttonContainer}
            >
                <YoYoText
                    type="subTitle"
                    bold
                    color={
                        isTakeSelected ? leftColor : MainStyle.colors.lightGray
                    }
                >
                    {left}
                </YoYoText>
                {isTakeSelected && (
                    <View
                        style={[
                            styles.underline,
                            {
                                backgroundColor: isTakeSelected
                                    ? leftColor
                                    : rightColor,
                            },
                        ]}
                    />
                )}
            </Pressable>

            {/* '보냈어요' (Give) */}
            <Pressable
                onPress={buttonGiveHandler}
                style={styles.buttonContainer}
            >
                <YoYoText
                    type="subTitle"
                    bold
                    color={
                        !isTakeSelected
                            ? rightColor
                            : MainStyle.colors.lightGray
                    }
                >
                    {right}
                </YoYoText>
                {!isTakeSelected && (
                    <View
                        style={[
                            styles.underline,
                            {
                                backgroundColor: isTakeSelected
                                    ? leftColor
                                    : rightColor,
                            },
                        ]}
                    />
                )}
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: "row",
    },
    buttonContainer: {
        flex: 1,
        alignItems: "center",
    },
    underline: {
        height: 2,
        width: "100%", // 밑줄 너비
        marginTop: 5,
    },
});
