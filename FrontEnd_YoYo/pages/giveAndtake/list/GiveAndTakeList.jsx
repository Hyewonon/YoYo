import { View, StyleSheet, FlatList } from "react-native";
import React, { useState } from "react";
import Container from "../../../components/common/Container";
import YoYoCard from "../../../components/card/Yoyo/YoYoCard";
import SearchBar from "../../../components/common/SearchBar";
import YoYoText from "../../../constants/YoYoText";
import Button from "../../../components/common/Button";
import TagList from "../../../components/common/TagList";
import { getRelations } from "../../../apis/https/relationApi";
import Loading from "../../../components/common/Loading";
import { useFocusEffect } from "@react-navigation/native";

export default function GiveAndTakeList({ navigation }) {
    const [selectedTag, setSelectedTag] = useState("all");
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [keyword, setKeyword] = useState("");

    useFocusEffect(() => {
        let isActive = true;
        async function fetchData() {
            try {
                const response = await getRelations(keyword);
                const tmpData = response.map((item) => ({
                    id: item.relationId,
                    oppositeId: item.oppositeId,
                    title: item.oppositeName,
                    description: item.description,
                    type: item.relationType.toLowerCase(),
                    give: item.totalReceivedAmount,
                    take: item.totalSentAmount,
                }));
                const filteredData =
                    selectedTag === "all" || selectedTag === ""
                        ? tmpData
                        : tmpData.filter((item) => item.type === selectedTag);
                if (isActive) {
                    setData(filteredData);
                    setIsLoading(false);
                }
            } catch (error) {
                if (isActive) {
                    setData([]);
                    setIsLoading(false);
                }
            }
        }
        fetchData();
        return () => {
            isActive = false;
        };
    });

    const clickDetailHandler = (item) => {
        navigation.navigate("GiveAndTakeDetail", { id: item.oppositeId });
    };
    const clickAddHandler = () => {
        navigation.navigate("SelectGiveAndTake");
    };
    function clickTag(type) {
        setSelectedTag(type);
    }
    function renderItem({ item }) {
        return (
            <YoYoCard data={item} onPress={() => clickDetailHandler(item)} />
        );
    }
    if (isLoading) return <Loading />;

    return (
        <>
            <Container>
                <View style={styles.searchContainer}>
                    <SearchBar
                        placeholder={"이름을 검색해 주세요."}
                        setKeyword={setKeyword}
                        keyword={keyword}
                    />
                </View>
                <View>
                    <TagList
                        onPress={clickTag}
                        selectedTag={selectedTag}
                        size={64}
                        all
                    />
                </View>
                {data && data.length === 0 ? (
                    <YoYoText type="md" bold center>
                        요요 목록이 없습니다.
                    </YoYoText>
                ) : (
                    <FlatList
                        data={data}
                        renderItem={renderItem}
                        keyExtractor={(item) => item.id}
                        style={styles.innerContainer}
                    />
                )}
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
