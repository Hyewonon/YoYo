import { View, StyleSheet, FlatList } from "react-native";
import React, { useEffect, useState } from "react";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../common/Input";
import { MainStyle } from "../../../constants/style";
import TagList from "../../common/TagList";
import { useNavigation } from "@react-navigation/native";
import YoYoCard from "../../card/Yoyo/YoYoCard";

export default function Detail({ setIsActive, person, setPerson, data }) {
    const navigation = useNavigation();
    const [description, setDescription] = useState("");
    const [selectedTag, setSelectedTag] = useState("all");
    const [selectedCard, setSelectedCard] = useState(-1);
    function clickCard(id) {
        if (id === selectedCard) {
            setSelectedCard(-1);
            setIsActive(false);
        } else {
            setSelectedCard(id);
            setIsActive(true);
        }
    }

    useEffect(() => {
        if (description.length > 0) {
            setIsActive(true);
            setPerson((prevPerson) => ({
                ...prevPerson,
                description: description,
            }));
        }
    }, [description]);

    function clickTag(type) {
        setSelectedTag(type);
    }

    return (
        <View>
            <View style={styles.container}>
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        이름 입력
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input text={person.name} notEditable />
                </View>
            </View>
            <View style={styles.container}>
                {data.length > 0 ? (
                    <FlatList
                        data={data}
                        renderItem={({ item }) => (
                            <YoYoCard
                                item={item}
                                onPress={() => clickCard(item.id)}
                                type="select"
                                selectedCard={selectedCard}
                            />
                        )}
                        style={styles.innerContainer}
                    ></FlatList>
                ) : (
                    <>
                        <View>
                            <YoYoText
                                type="title"
                                bold
                                color={MainStyle.colors.main}
                            >
                                태그선택
                            </YoYoText>
                        </View>
                        <View style={styles.tagContainer}>
                            <TagList
                                onPress={clickTag}
                                selectedTag={selectedTag}
                                size={76}
                            />
                        </View>

                        <View>
                            <YoYoText
                                type="title"
                                bold
                                color={MainStyle.colors.main}
                            >
                                인적사항
                            </YoYoText>
                        </View>
                        <View style={styles.textContainer}>
                            <Input
                                placeholder={"추가 인적사항을 기록해주세요."}
                                onChange={setDescription}
                                text={description}
                            />
                        </View>
                    </>
                )}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        marginTop: 30,
    },
    textContainer: {
        marginTop: 30,
    },
    tagContainer: {
        marginVertical: 12,
    },
    innerContainer: {
        marginBottom: 16,
    },
});
