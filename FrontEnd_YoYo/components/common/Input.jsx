import { View, Text, TextInput, StyleSheet } from "react-native";
import React, { useState } from "react";
import { MainStyle } from "../../constants/style";

export default function Input({
    type,
    placeholder,
    onChange,
    isError,
    text,
    notEditable,
    errorMessage
}) {
    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => setIsFocused(true);

    const borderColor = isError
        ? MainStyle.colors.error // 에러가 있을 때의 색상
        : isFocused
            ? MainStyle.colors.main // 포커스될 때의 색상
            : MainStyle.colors.lighter; // 기본 색상

    return (
        <View style={styles.inputContainer}>
            <TextInput
                style={[styles.input, { borderBottomColor: borderColor }]}
                placeholder={placeholder}
                placeholderTextColor={MainStyle.colors.lighter}
                onChangeText={onChange}
                onFocus={handleFocus}
                onBlur={() => setIsFocused(text.length > 0)}
                secureTextEntry={type === "password" ? true : false}
                keyboardType={type === "phoneNumber" ? "number-pad" : "default"}
                autoCapitalize="none"
                value={text}
                editable={notEditable ? false : true}
            />
            {isError && <Text style={styles.error}>※ {errorMessage}</Text>}
        </View>
    );
}

const styles = StyleSheet.create({
    inputContainer: {
        paddingVertical: 4,
        width: "100%",
    },
    input: {
        borderBottomWidth: 1,
        fontSize: MainStyle.fontSize.md,
        paddingHorizontal: 4,
        paddingVertical: 8,
    },
    error: {
        color: MainStyle.colors.error,
        fontSize: MainStyle.fontSize.subContent,
    },
});
