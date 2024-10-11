import { StyleSheet } from "react-native";
import LottieView from "lottie-react-native";
import React from "react";
import Container from "./Container";

export default function Loading() {
    return (
        <Container>
            <LottieView
                style={styles.iconContainer}
                source={require("../../assets/loadingIcon.json")}
                autoPlay
                loop
            />
        </Container>
    );
}

const styles = StyleSheet.create({
    iconContainer: {
        flex: 1,
        justifyContent: "center",
    },
});
