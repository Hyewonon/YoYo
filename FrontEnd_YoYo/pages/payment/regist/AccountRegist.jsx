import { Alert, Image, View } from 'react-native';
import React, { useLayoutEffect, useState } from 'react';
import Container from '../../../components/common/Container';
import YoYoText from '../../../constants/YoYoText';
import IconButton from '../../../components/common/IconButton';
import SelectBank from './SelectBank';
import RegistAccountNumber from './RegistAccountNumber';
import RegistAuthNumber from './RegistAuthNumber';
import CompleteRegist from './CompleteRegist';
import { sendWon } from '../../../apis/https/payApi';
// {
//   "memberId": 1,
//   "bankName": "싸피은행",
//   "accountNo": "9997560764180754"
// }
const bankImages = {
  KB국민은행: require("../../../assets/svg/banks/KB국민은행.png"),
  신한은행: require("../../../assets/svg/banks/신한은행.png"),
  하나은행: require("../../../assets/svg/banks/하나은행.png"),
  IBK기업은행: require("../../../assets/svg/banks/IBK기업은행.png"),
  카카오뱅크: require("../../../assets/svg/banks/카카오뱅크.png"),
  NH농협은행: require("../../../assets/svg/banks/NH농협은행.png"),
  SC제일은행: require("../../../assets/svg/banks/SC제일은행.png"),
  토스뱅크: require("../../../assets/svg/banks/토스뱅크.png"),
  우리은행: require("../../../assets/svg/banks/우리은행.png")
};

export default function AccountRegist({ navigation }) {
  const [stage, setStage] = useState(0);
  const [info, setInfo] = useState({
    accountNumber: "",
    bankName: "",
    isAuthenticated: false,
    pin: ""
  })
  const [accountAuthNumber, setAccountAuthNumber] = useState("");
  function clickPrev() {
    if (stage === 0) {
      navigation.goBack();
    } else {
      setStage(stage - 1);
    }
  }
  useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => {
        if (stage <= 2) {
          return <IconButton icon="arrow-back-outline" size={24} onPress={clickPrev} />
        }
      },
      headerTitle: () => headerTitle(),

    });
  }, [navigation, info.bankName, stage]);
  function headerTitle() {
    if (stage === 0) {
      return <YoYoText type="md" bold>은행 선택</YoYoText>;
    } else if (stage === 1 && info.bankName.length > 0) {
      return (
        <View style={{ flexDirection: 'row', alignItems: 'center' }}>
          <Image source={bankImages[info.bankName]} style={{ width: 24, height: 24, marginRight: 8 }} />
          <YoYoText type="md" bold>계좌번호 입력</YoYoText>
        </View>
      );
    } else if (stage === 2 && info.accountNumber.length > 0) {
      return (
        <YoYoText type="md" bold>계좌 인증</YoYoText>
      )
    } else if (stage === 3) {
      return (<YoYoText type='subTitle' bold>등록완료</YoYoText>)
    }
  }



  function clickBank(bank) {
    setInfo((prev) => ({
      ...prev,
      bankName: bank
    }))
    setStage(1);
  }
  async function getAuth() {
    try {
      const response = await sendWon({ accountNumber: info.accountNumber })
      if (response.isSuccess)
        Alert.alert("송금 완료", "1원 송금이 완료되었습니다.", [
          {
            text: "확인",
            onPress: () => setStage(stage + 1)
          }
        ])
    } catch (error) {
      Alert.alert("계좌번호를 확인해주세요.")
    }
  }
  function clickAuth() {
    if (info.accountNumber.length > 10) {
      getAuth()
      setStage(stage + 1)
    } else {
      alert("계좌번호를 입력하세요.")
    }
  }
  function clickRegistPasswordHandler() {
    navigation.navigate("RegistPayPassword", { data: { title: "등록 완료", content: "결제 비밀번호 등록이 완료되었습니다.", info: info }, type: 'account' })
  }
  return (
    <Container>
      {stage === 0 && (
        <SelectBank onPress={clickBank} />
      )}
      {stage === 1 && <RegistAccountNumber number={info.accountNumber} setNumber={(text) => {
        setInfo((prev) => ({
          ...prev,
          accountNumber: text
        }))
      }} onPress={clickAuth} />}
      {stage === 2 && <RegistAuthNumber bank={info.bankName} bankImg={bankImages[info.bankName]} accountNumber={info.accountNumber} setAccountAuthNumber={setAccountAuthNumber} onPress={() => {
        setStage(3)
        setInfo((prev) => ({
          ...prev,
          isAuthenticated: true
        }))
      }} />}
      {stage === 3 && <CompleteRegist bank={bankImages[info.bankName]} accountNumber={info.accountNumber} onPress={clickRegistPasswordHandler} />}
    </Container>
  );
}


