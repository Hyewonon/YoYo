import { View, StyleSheet, Alert } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import PayPasswordInput from '../../../components/pay/PayPasswordInput'
import IconButton from '../../../components/common/IconButton'
import NumberPad from '../../../components/pay/NumberPad'
import { managePay, registAccount } from '../../../apis/https/payApi'

export default function RegistPayPassword({ navigation, route }) {
  const { data, type } = route.params
  const [payPassword, setPayPassword] = useState([-1, -1, -1, -1, -1, -1])
  const [checkPayPassword, setCheckPayPassword] = useState([-1, -1, -1, -1, -1, -1])
  const [stage, setStage] = useState(1);
  function clickCloseHandler() {
    navigation.goBack()
  }
  async function insertAccount(info) {
    try {
      const response = await registAccount(info)
      if (response.isSuccess) {
        navigation.navigate("AfterPassword", {
          data: data
        })
      } else {
        Alert.alert("계좌 등록 간 문제가 발생했습니다.")
      }
    } catch (error) {
      Alert.alert("계좌 등록 간 문제가 발생했습니다.")
      console.log(error)
    }
  }
  async function sendPay(money) {
    const type = data.title.split(" ")[0]
    try {
      let response;
      if (type === '충전하기') {
        response = await managePay("charge", { payAmount: money })
      } else if (type === '옮기기') {
        response = await managePay('refund', { payAmount: money })
      } else {
        response = await managePay('transfer', data)
      }
      if (response.isSuccess) {
        navigation.navigate("AfterPassword", {
          data: data
        })
      }
    } catch (error) {
      Alert.alert("송금 간 문제가 발생하였습니다.", "계좌 또는 페이 잔액을 확인해주세요.", [
        {
          text: "확인",
          onPress: () => navigation.navigate("Payment")
        }
      ])
    }
  }
  function next() {
    if (type === 'pay') {
      sendPay(data.money)
    } else {
      if (stage === 1) {
        setStage(2)
      } else if (stage === 2) {
        if (payPassword.join("") === checkPayPassword.join("")) {
          data.info.pin = checkPayPassword.join("")
          insertAccount(data.info)
        } else {
          alert("비밀번호 확인좀")
          setCheckPayPassword([-1, -1, -1, -1, -1, -1])
        }
      }
    }

  }
  return (
    <>
      <Container>
        <View style={styles.iconButtonContainer}><IconButton icon="close" size={24} onPress={clickCloseHandler} /></View>
        <View style={styles.innerContainer}>
          <YoYoText type="md" bold center>{stage === 1 ? <>결제 비밀번호 <YoYoText type="subTitle" bold color={MainStyle.colors.main}>6자리</YoYoText>를 입력해주세요.</> : "확인을 위해 다시 한번 입력해주세요."}</YoYoText>
          <View style={styles.rowContainer}>
            {stage === 1 ? payPassword.map((num, idx) => <PayPasswordInput number={num} key={idx} />) : checkPayPassword.map((num, idx) => <PayPasswordInput number={num} key={idx} />)}
          </View>
        </View>
      </Container>
      <NumberPad
        setPayPassword={stage === 1 ? setPayPassword : setCheckPayPassword} next={next} />
    </>
  )
}
const styles = StyleSheet.create({
  iconButtonContainer: {
    flexDirection: 'row',
    width: '100%',
    justifyContent: 'flex-end',
    alignItems: 'flex-end',
    marginTop: 24
  },
  innerContainer: {
    marginTop: 80,
    gap: 36,
    alignItems: 'center'
  },
  rowContainer: {
    justifyContent: 'space-between',
    flexDirection: 'row',
    gap: 12
  }
})