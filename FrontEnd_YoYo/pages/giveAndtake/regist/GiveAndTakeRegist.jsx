import { View, StyleSheet } from "react-native";
import Container from "../../../components/common/Container";
import React, { useState, useLayoutEffect } from "react";
import Next from "../../../components/common/Next";
import IconButton from "../../../components/common/IconButton";
import Name from "../../../components/giveAndTake/regist/Name";
import Detail from "../../../components/giveAndTake/regist/Detail";
import Event from "../../../components/giveAndTake/regist/Event";
import Money from "../../../components/giveAndTake/regist/Money";
import { postTransaction } from "../../../apis/https/transactionApi";

export default function GiveAndTakeRegist({ navigation, route }) {
    const { type } = route.params;
    const [stage, setStage] = useState(0);
    const [isActive, setIsActive] = useState(false);
    const [person, setPerson] = useState({
        transactionType: type === 1 ? "RECEIVE" : "SEND",
        memberId: 0,
        name: "",
        relationType: "",
        description: "",
        eventId: 0,
        eventName: "",
        amount: 0,
        memo: "",
    });

    function clickNextButton(person) {
        if (isActive) {
            if (stage < 3) {
                setStage(stage + 1);
            } else {
                postTransaction(person);
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
                    onPress={() => clickNextButton(person)}
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
    }, [navigation, stage, person, isActive]);

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
                    />
                )}
            </View>
            <View>
                {stage === 2 && (
                    <Event
                        type={type}
                        person={person}
                        setPerson={setPerson}
                        setIsActive={setIsActive}
                    />
                )}
            </View>
            <View>
                {stage === 3 && (
                    <Money
                        type={type}
                        person={person}
                        setPerson={setPerson}
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
