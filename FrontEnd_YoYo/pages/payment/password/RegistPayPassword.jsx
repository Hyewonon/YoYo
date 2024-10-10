import { View, Text, SafeAreaView, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import PayPasswordInput from '../../../components/pay/PayPasswordInput'
import IconButton from '../../../components/common/IconButton'
import NumberPad from '../../../components/pay/NumberPad'

export default function RegistPayPassword({ navigation, route }) {
  const data = route.params.data
  const type = route.params.type
  const [payPassword, setPayPassword] = useState([-1, -1, -1, -1, -1, -1])
  const [checkPayPassword, setCheckPayPassword] = useState([-1, -1, -1, -1, -1, -1])
  const [stage, setStage] = useState(1);
  function clickCloseHandler() {
    navigation.goBack()
  }
  function next() {
    if (type === 'pay') {
      navigation.navigate("AfterPassword", {
        data: data
      })
    } else {
      if (stage === 1) {
        setStage(2)
      } else if (stage === 2) {
        if (payPassword.join("") === checkPayPassword.join("")) {
          navigation.navigate("AfterPassword", {
            data: data
          })
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