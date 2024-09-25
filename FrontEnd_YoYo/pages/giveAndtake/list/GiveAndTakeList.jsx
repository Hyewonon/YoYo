import { View, StyleSheet, FlatList } from "react-native";
import React, { useState } from "react";
import Container from "../../../components/common/Container";
import YoYoCard from "../../../components/card/Yoyo/YoYoCard";
import SearchBar from "../../../components/common/SearchBar";
import YoYoText from "../../../constants/YoYoText";
import Button from "../../../components/common/Button";
import TagList from "../../../components/common/TagList";

export default function GiveAndTakeList({ navigation }) {
    const [selectedTag, setSelectedTag] = useState("all");

    const DATA = [
        {
            id: "1",
            title: "김현태",
            description: "고등학교 친구",
            type: "friend",
        },
        {
            id: "2",
            title: "최광림",
            description: "주먹왕",
            type: "family",
        },
    ];

    const [data, setData] = useState(DATA);

    const clickDetailHandler = (item) => {
        navigation.navigate("GiveAndTakeDetail", { id: item.id });
    };
    const clickAddHandler = () => {
        navigation.navigate("SelectGiveAndTake");
    };
    function clickTag(type) {
        setSelectedTag(type);
        if (type === "all") {
            setData(DATA);
        } else {
            setData(DATA.filter((item) => item.type === type));
        }
    }
    function renderItem({ item }) {
        return (
            <YoYoCard data={item} onPress={() => clickDetailHandler(item)} />
        );
    }

    return (
        <>
            <Container>
                <View style={styles.searchContainer}>
                    <SearchBar placeholder={"이름을 검색해 주세요."} />
                </View>
                <View>
                    <TagList
                        onPress={clickTag}
                        selectedTag={selectedTag}
                        size={64}
                        all
                    />
                </View>
                <FlatList
                    data={data}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.id}
                    style={styles.innerContainer}
                ></FlatList>
            </Container>
            <Button type={"fill"} onPress={clickAddHandler}>
                <YoYoText bold>거래내역 추가하기</YoYoText>
            </Button>
        </>
    );
}

const styles = StyleSheet.create({
    searchContainer: {
        marginBottom: 4,
    },
    innerContainer: {
        marginBottom: 16,
    },
    iconContainer: {
        alignItems: "center",
    },
});
