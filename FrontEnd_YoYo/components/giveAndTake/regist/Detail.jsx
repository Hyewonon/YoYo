import { View, StyleSheet, FlatList, Pressable, Modal, ScrollView } from "react-native";
import React, { useEffect, useState } from "react";
import { MainStyle } from "../../../constants/style";
import { getRelations } from "../../../apis/https/relationApi";
import YoYoText from "../../../constants/YoYoText";
import Input from "../../common/Input";
import TagList from "../../common/TagList";
import YoYoCard from "../../card/Yoyo/YoYoCard";
import Button from "../../common/Button";
import SelectModal from "../../ocr/SelectModal";
import Container from "../../common/Container";

export default function Detail({ setIsActive, person, setPerson }) {
    const [relationData, setRelationData] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    useEffect(() => {
        if (person.description) {
            setIsActive(true);
        } else {
            setIsActive(false);
        }
    }, [person.description, person.relationType]);
    useEffect(() => {
        async function fetchRelationData(name) {
            const response = await getRelations(name);
            if (response != null) {
                const tmpData = response.map((item) => ({
                    id: item.relationId,
                    oppositeId: item.oppositeId,
                    name: item.oppositeName,
                    title: item.oppositeName,
                    description: item.description,
                    type: item.relationType.toLowerCase(),
                    give: item.totalReceivedAmount,
                    take: item.totalSentAmount,
                }));
                setRelationData(tmpData);
            }
        }
        fetchRelationData(person.name);
    }, [person.name]);
    function clickTag(type) {
        setPerson((prevPerson) => ({
            ...prevPerson,
            relationType: type.toUpperCase(),
        }));
    }
    return (
        <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
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
                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        태그선택
                    </YoYoText>
                </View>
                <View style={styles.tagContainer}>
                    <TagList
                        onPress={clickTag}
                        selectedTag={
                            person.relationType
                                ? person.relationType.toLowerCase()
                                : "all"
                        }
                        size={76}
                    />
                </View>

                <View>
                    <YoYoText type="title" bold color={MainStyle.colors.main}>
                        인적사항
                    </YoYoText>
                </View>
                <View style={styles.textContainer}>
                    <Input
                        placeholder={"추가 인적사항을 기록해주세요."}
                        onChange={(text) =>
                            setPerson((prevPerson) => ({
                                ...prevPerson,
                                description: text,
                            }))
                        }
                        text={person.description}
                    />
                </View>
                {relationData.length > 0 && (
                    <Pressable onPress={() => setIsModalOpen(true)}>
                        <YoYoText type="md" bold color={MainStyle.colors.red}>
                            ※지인 목록에서 선택하기
                        </YoYoText>
                    </Pressable>
                )}

                <Modal
                    visible={isModalOpen}
                    animationType="fade"
                    transparent={true}
                    onRequestClose={() => setIsModalOpen(false)}
                >
                    <SelectModal
                        onPress={() => setIsModalOpen(false)}
                        data={relationData}
                        friend={person}
                        setFriend={setPerson}
                        type="event"
                    />
                </Modal>
            </View>
        </ScrollView>
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
    selectContainer: {
        flexDirection: "column-reverse",
        alignItems: "center",
        padding: 16,
        backgroundColor: MainStyle.colors.main,
    },
});
