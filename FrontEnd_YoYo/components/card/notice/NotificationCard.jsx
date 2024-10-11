import { View, Text, Pressable } from "react-native";
import React from "react";
import Card from "../card/Card";
import YoYoText from "../../constants/YoYoText";

export default function NotificationCard({ item, onLongPress }) {
    return (
        <Card onPress={() => onLongPress(item.id)}>
            <YoYoText>{item.date}</YoYoText>
            <YoYoText>{item.name} 님이 마음을 전했습니다</YoYoText>
        </Card>
    );
}
