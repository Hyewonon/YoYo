import React, { useContext, useEffect, useState } from "react";
import MainPageCard from "../../components/card/mainPage/MainPageCard";
import PayCard from "../../components/card/mainPage/PayCard";
import Container from "../../components/common/Container";
import { useNavigation } from "@react-navigation/native";
import { StyleSheet, View } from "react-native";
import Header from "../../components/header/Header";
import { getPay } from "../../apis/https/payApi";

export default function MainPage() {
    const [payInfo, setPayInfo] = useState()
    const navigation = useNavigation();
    function clickPayCardHandler() {
        navigation.navigate("계좌등록");
    }
    function clickEventHandler() {
        navigation.navigate("EventList");
    }
    function clickYoyoHandler() {
        navigation.navigate("GiveAndTake");
    }
    async function getPayInfo() {
        const response = await getPay();
        setPayInfo(response)

    }
    useEffect(() => {
        getPayInfo();
    }, [])
    return (
        <Container>
            <Header />
            {payInfo &&
                <PayCard
                    data={payInfo}
                    onPress={clickPayCardHandler}
                    account={payInfo}
                />}
            <MainPageCard
                title={"Event"}
                subTitle={"경조사를 추가하Go" + "\n" + "경조사비를 관리해Yo!"}
                type="event"
                onPress={clickEventHandler}
            />
            <MainPageCard
                title={"Yo! Yo!"}
                subTitle={"주변인들과의" + "\n" + "경조사비 현황을 확인해Yo!"}
                type="yoyo"
                onPress={clickYoyoHandler}
            />
        </Container>
    );
}
const styles = StyleSheet.create({
    header: {
        marginVertical: 24,
    },
});
