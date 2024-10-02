import { View, StyleSheet, Image, KeyboardAvoidingView, Platform, ScrollView, TouchableWithoutFeedback, Keyboard, Alert } from 'react-native';
import React, { useEffect, useRef, useState } from 'react';
import YoYoText from '../../../constants/YoYoText';
import { MainStyle } from '../../../constants/style';
import Detail from '../../../assets/svg/계좌인증예시.svg';
import AccountAuthInput from '../../../components/pay/AccountAuthInput';
import Container from '../../../components/common/Container';
import Button from '../../../components/common/Button';
import { checkWon } from '../../../apis/https/payApi';


export default function RegistAuthNumber({ bank, accountNumber, setAccountAuthNumber, onPress, bankImg }) {
  const [first, setFirst] = useState('');
  const [second, setSecond] = useState('');
  const [third, setThird] = useState('');
  const [fourth, setFourth] = useState('');
  const ref_input = [useRef(null), useRef(null), useRef(null), useRef(null)];
  useEffect(() => {
    ref_input[0].current.focus()
  }, [])

  function handleChange(text, index) {
    const filteredText = text.replace(/[^0-9]/g, '');

    if (index === 0) {
      setFirst(filteredText);
      if (filteredText) {
        ref_input[1].current.focus();
      }
    } else if (index === 1) {
      setSecond(filteredText);
      if (filteredText) {
        ref_input[2].current.focus();
      } else {
        ref_input[0].current.focus();
      }
    } else if (index === 2) {
      setThird(filteredText);
      if (filteredText) {
        ref_input[3].current.focus();
      } else {
        ref_input[1].current.focus();
      }
    } else if (index === 3) {
      setFourth(filteredText);
      if (!filteredText) {
        ref_input[2].current.focus();
      }
    }
  }

  function isValid() {
    return first && second && third && fourth;
  }
  async function checkAuth() {
    const auth = first.toString() + second.toString() + third.toString() + fourth.toString()
    try {
      const response = await checkWon({ accountNumber: accountNumber, authCode: auth });
      if (response.isSuccess) {
        setAccountAuthNumber(auth)
        onPress();
      } else {
        Alert.alert("인증번호를 확인해주세요.")
      }

    } catch (error) {
      console.log(error)
    }
  }

  function clickButton() {
    if (isValid()) {
      checkAuth()
    }
  }
  return (
    <>
      <KeyboardAvoidingView
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={{ flex: 1 }}
      >
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
          <ScrollView contentContainerStyle={{ flexGrow: 1 }} keyboardShouldPersistTaps='handled'>
            <Container>
              <View style={styles.container}>
                <YoYoText type="md" bold center>1원을 보냈습니다.</YoYoText>
                <YoYoText type="desc" bold center color={MainStyle.colors.lightGray}>
                  입금내역에 표시된 숫자 4자리를 입력해주세요.
                </YoYoText>
                <Detail />
                <View style={styles.accountContainer}>
                  <Image source={bankImg} />
                  <YoYoText type="desc" bold>{bank}</YoYoText>
                </View>
                <View style={styles.accountContainer}>
                  <YoYoText type="md" bold>{"   "}계좌번호 : {accountNumber} </YoYoText>
                </View>
                <View style={styles.inputContainer}>
                  <AccountAuthInput
                    ref={ref_input[0]}
                    number={first}
                    onChangeText={(text) => handleChange(text, 0)}
                  />
                  <AccountAuthInput
                    ref={ref_input[1]}
                    number={second}
                    onChangeText={(text) => handleChange(text, 1)}
                  />
                  <AccountAuthInput
                    ref={ref_input[2]}
                    number={third}
                    onChangeText={(text) => handleChange(text, 2)}
                  />
                  <AccountAuthInput
                    ref={ref_input[3]}
                    number={fourth}
                    onChangeText={(text) => handleChange(text, 3)}
                  />
                </View>
              </View>
            </Container>
          </ScrollView>
        </TouchableWithoutFeedback>
      </KeyboardAvoidingView>
      <Button width="100%" type={isValid() ? "fill" : "inactive"} radius={16} onPress={clickButton}>
        <YoYoText type="md" bold>확인</YoYoText>
      </Button>
    </>
  );
}
const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    gap: 16
  },
  accountContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    width: '100%',
    marginLeft: 60,
    gap: 4
  },
  inputContainer: {
    flexDirection: 'row',
    gap: 16
  }
});

