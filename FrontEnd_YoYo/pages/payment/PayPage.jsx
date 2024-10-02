import { View, Text, StyleSheet } from "react-native";
import React, { useEffect, useState } from "react";
import Container from "../../components/common/Container";
import Header from "../../components/header/Header";
import YoYoText from "../../constants/YoYoText";
import MoneyCard from "../../components/card/mainPage/MoneyCard";
import PayButton from "../../components/pay/PayButton";
import { useNavigation } from "@react-navigation/native";
import { getPay } from "../../apis/https/payApi";
import Button from "../../components/common/Button";
const data = {
    Migration: {
        mode: 1,
        type: "Migration",
        title: "옮기기",
        text: "내 계좌로 돈을 옮겨요.",
        style: { width: 55, height: 120 },
    },
    Charge: {
        mode: 1,
        type: "Charge",
        title: "충전",
        text: "요요페이에 돈을 충전해요.",
        style: { width: 90, height: 120 },
    },
    PayList: {
        mode: 2,
        type: "PayList",
        title: "거래 내역",
        text: "요요페이의 거래 내역을 \n확인해요.",
        style: { width: 80, height: 100 },
    },
    MyAccount: {
        mode: 2,
        type: "MyAccount",
        title: "내 계좌 관리",
        text: "요요페이와 연결된 \n계좌를 관리해요.",
        style: { width: 80, height: 100 },
    },
};
export default function PayPage() {
    const [payInfo, setPayInfo] = useState()
    const navigation = useNavigation();
    const navigationList = {
        Migration: () =>
            navigation.navigate("돈보내기", {
                title: "옮기기",
            }),
        Charge: () => navigation.navigate("돈보내기", { title: "충전하기" }),
        PayList: (data) => navigation.navigate("Pay List", { payInfo: data }),
        MyAccount: () => navigation.navigate("ManageAccount"),
    };
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
                <View style={styles.moneyContainer}>
                    <YoYoText type={"subTitle"} bold>
                        {payInfo.memberName}님, 반갑습니다!
                    </YoYoText>
                    <MoneyCard account={payInfo} money={payInfo.balance} />
                </View>}

            <View style={styles.buttonContainer}>
                {payInfo ? <><View style={styles.firstContainer}>
                    <View style={styles.migrationContainer}>
                        <PayButton
                            data={data.Migration}
                            onPress={navigationList.Migration}
                        />
                    </View>

                    <View style={styles.chargeContainer}>
                        <PayButton
                            data={data.Charge}
                            onPress={navigationList.Charge}
                        />
                    </View>
                </View>
                    <View>
                        <PayButton
                            data={data.PayList}
                            onPress={() => navigationList.PayList(payInfo)}
                        />
                    </View>
                    <View>
                        <PayButton
                            data={data.MyAccount}
                            onPress={navigationList.MyAccount}
                        />
                    </View></> : <><Button type="normal" width="100%" radius={24} onPress={() => navigation.navigate("계좌등록")}><YoYoText type="md" bold>계좌 등록하러 가기</YoYoText></Button></>}
            </View>
        </Container>
    );
}

const styles = StyleSheet.create({
    moneyContainer: {
        rowGap: 10,
    },
    buttonContainer: {
        rowGap: 16,
        paddingVertical: 16,
    },
    firstContainer: {
        flexDirection: "row",
        columnGap: 10,
    },
    migrationContainer: {
        flex: 1,
    },
    chargeContainer: {
        flex: 1,
    },
});
