import { Pressable, View, StyleSheet } from "react-native";
import React from "react";
import { MainStyle } from "../../constants/style";
import YoYoText from "../../constants/YoYoText";

export default function WhiteButton({ children, text, onPress }) {
    return (
        <Pressable
            onPress={onPress}
            style={({ pressed }) => [
                styles.container,
                pressed && styles.pressed,
            ]}
        >
            <View style={styles.iconContainer}>{children}</View>
            <View>
                <YoYoText type="subTitle" bold>
                    {text}
                </YoYoText>
            </View>
        </Pressable>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: "white", // 흰색 배경
        borderRadius: 12,
        padding: 24,
        width: 150,
        height: 150,
        elevation: 5,
        alignItems: "center",
        justifyContent: "center",
        shadowColor: MainStyle.colors.black,
        shadowOpacity: 0.25,
        shadowOffset: { width: 0, height: 2 },
        shadowRadius: 4,
        margin: 10,
    },
    pressed: {
        opacity: 0.75, // 버튼을 눌렀을 때 살짝 투명해지도록 처리
    },
    iconContainer: {
        marginBottom: 8, // 아이콘과 텍스트 사이의 간격
    },
});
