import { View, Pressable, StyleSheet } from "react-native";
import React from "react";
import { Ionicons } from "@expo/vector-icons";

export default function IconButton({ icon, size, onPress }) {
    return (
        <Pressable
            onPress={onPress}
            style={({ pressed }) => pressed && styles.pressed}
        >
            <Ionicons name={icon} size={size} />
        </Pressable>
    );
}
const styles = StyleSheet.create({
    pressed: {
        opacity: 0.75,
    },
});
