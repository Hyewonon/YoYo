import { View, StyleSheet } from "react-native";
import Container from "../../../components/common/Container";
import React, { useState, useLayoutEffect } from "react";
import Next from "../../../components/common/Next";
import IconButton from "../../../components/common/IconButton";
import Name from "../../../components/giveAndTake/regist/Name";
import Detail from "../../../components/giveAndTake/regist/Detail";
import Event from "../../../components/giveAndTake/regist/Event";
import Money from "../../../components/giveAndTake/regist/Money";

export default function GiveAndTakeRegist({ navigation, route }) {
    const [stage, setStage] = useState(0);
    const [isActive, setIsActive] = useState(false);
    const [person, setPerson] = useState({
        name: "",
        tag: "",
        description: "",
    });
    const { type } = route.params;

    const detailList = [
        {
            id: "1",
            title: "First Item",
        },
        {
            id: "2",
            title: "Second Item",
        },
    ];

    const eventList = [
        {
            id: 1,
            title: "결혼식",
            name: "김현태",
            date: "24.08.29",
            position: "서울시 강남구",
        },
        {
            id: 2,
            title: "돌잔치",
            name: "이찬진",
            date: "24.09.04",
            position: "서울시 강남구",
        },
    ];

    function clickNextButton() {
        if (isActive) {
            if (stage < 3) {
                setStage(stage + 1);
            } else {
                navigation.navigate("GiveAndTake");
            }
        }
        setIsActive(false);
    }

    function clickPrevButton() {
        if (stage === 0) {
            navigation.goBack();
        } else {
            setStage(stage - 1);
        }
    }
    useLayoutEffect(() => {
        navigation.setOptions({
            headerRight: () => (
                <Next
                    isActive={isActive}
                    onPress={clickNextButton}
                    final={stage === 3}
                />
            ),
            headerLeft: () => (
                <IconButton
                    icon="arrow-back-outline"
                    size={24}
                    onPress={clickPrevButton}
                />
            ),
        });
    }, [navigation, stage, isActive]);

    return (
        <Container>
            <View>
                {stage === 0 && (
                    <Name setIsActive={setIsActive} setPerson={setPerson} />
                )}
            </View>
            <View>
                {stage === 1 && (
                    <Detail
                        setIsActive={setIsActive}
                        person={person}
                        setPerson={setPerson}
                        data={detailList}
                    />
                )}
            </View>
            <View>
                {stage === 2 && (
                    <Event
                        type={type}
                        data={eventList}
                        setIsActive={setIsActive}
                    />
                )}
            </View>
            <View>
                {stage === 3 && (
                    <Money
                        type={type}
                        data={eventList}
                        setIsActive={setIsActive}
                    />
                )}
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    nextContainer: {
        flexDirection: "row-reverse",
    },
    titleContainer: {
        marginTop: 30,
    },
    textContainer: {
        marginTop: 30,
    },
});
