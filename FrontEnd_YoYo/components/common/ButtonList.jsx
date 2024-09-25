import { StyleSheet, View } from "react-native";
import React, { useEffect } from "react";
import Button from "./Button";
import { MainStyle } from "../../constants/style";

export default function ButtonList({ size, setAmount }) {
    let money = [
        { type: "1000", name: "+1000" },
        { type: "5000", name: "+5000" },
        { type: "10000", name: "+10000" },
        { type: "50000", name: "+50000" },
    ];

    function clickAmount(item) {
        setAmount((prevAmount) => prevAmount + parseInt(item.type, 10));
    }

    return (
        <View>
            <View style={styles.buttonContainer}>
                {money.map((item) => (
                    <Button
                        key={item.type}
                        type="normal"
                        onPress={() => clickAmount(item)}
                        radius={24}
                        width={size}
                    >
                        {item.name}
                    </Button>
                ))}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    buttonContainer: {
        marginVertical: 24,
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
    },
});
