import { View, StyleSheet } from "react-native";
import React from "react";
import { MainStyle } from "../../constants/style";
import YoYoText from "../../constants/YoYoText";

const tagColor = {
    all: MainStyle.tagColors.all,
    friend: MainStyle.tagColors.friend,
    family: MainStyle.tagColors.family,
    company: MainStyle.tagColors.company,
    etc: MainStyle.tagColors.etc,
};

export default function Tag({ type, margin, children, width }) {
    return (
        <View
            style={[
                styles.container,
                {
                    width: width,
                    backgroundColor: tagColor[type],
                    marginHorizontal: margin ? margin : 0,
                },
            ]}
        >
            <YoYoText color="white" type="desc" bold>
                {children}
            </YoYoText>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        alignItems: "center",
        justifyContent: "center",
        paddingVertical: 8,
        paddingHorizontal: 16,
        borderRadius: 32,
        alignSelf: "flex-start",
    },
});
