import { View, Text } from "react-native";
import React from "react";
import ContentLoader, { Rect } from "react-content-loader/native";
import { MainStyle } from "../../constants/style";

const giveAndTakeLoader = (props) => {
    return (
        <ContentLoader
            width={400}
            height={120}
            backgroundColor={MainStyle.colors.lightGray}
            {...props}
        >
            {/* Title (Name) */}
            <Rect x="0" y="10" rx="5" ry="5" width="80" height="30" />

            {/* Tag (Type) */}
            <Rect x="90" y="10" rx="15" ry="15" width="80" height="30" />

            {/* Description */}
            <Rect x="0" y="50" rx="5" ry="5" width="250" height="20" />

            {/* Description */}
            <Rect x="0" y="80" rx="5" ry="5" width="400" height="35" />
        </ContentLoader>
    );
};

export default giveAndTakeLoader;
