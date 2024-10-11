import { View, Text, ActivityIndicator, StyleSheet } from "react-native";
import React from "react";
import { MainStyle } from "../../constants/style";
import Container from "./Container";

export default function LoadingSpinner() {
    return (
        <Container>
            <View style={styles.iconContainer}>
                <ActivityIndicator size="large" color={MainStyle.colors.main} />
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    iconContainer: {
        flex: 1,
        justifyContent: "center",
    },
});
