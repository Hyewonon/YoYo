import { View, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import YoYoText from '../../../constants/YoYoText'
import { CheckBox } from './../../../components/common/CheckBox';
import Button from '../../../components/common/Button';
import IconButton from './../../../components/common/IconButton';

export default function AccountAgree({ onPress, setIsBottomUp }) {
  const [isAll, setIsAll] = useState(false)
  const [isFirst, setIsFirst] = useState(false)
  const [isSecond, setIsSecond] = useState(false)
  function clickAllHandler() {
    if (isAll) {
      setIsAll(false)
      setIsFirst(false)
      setIsSecond(false)
    } else {
      setIsAll(true)
      setIsFirst(true)
      setIsSecond(true)
    }
  }
  function isValidate() {
    return isFirst && isSecond
  }
  function clickNextHandler() {
    if (isValidate()) onPress()
    else alert("동의해라")
  }
  return (
    <View style={styles.container}>
      <View style={{ position: "relative" }}>
        <YoYoText type="md" bold center>계좌 등록 약관 동의</YoYoText>
        <View style={{ position: 'absolute', top: 6, right: 0 }}>
          <IconButton icon="close" size={24} onPress={() => setIsBottomUp(false)} />
        </View>
      </View>
      <View style={styles.rowContainer}>
        <YoYoText type="desc" bold>서비스 이용 전체 동의</YoYoText>
        <CheckBox checked={isAll} onPress={clickAllHandler} />
      </View>
      <View style={styles.rowContainer}>
        <YoYoText type="desc" bold>[필수] 자동이체 출금 동의</YoYoText>
        <CheckBox checked={isFirst} onPress={() => {
          setIsFirst(!isFirst)
          if (isAll) setIsAll(false)
        }
        } />
      </View>
      <View style={styles.rowContainer}>
        <YoYoText type="desc" bold>[필수] 개인정보 제 3자 제공 동의</YoYoText>
        <CheckBox checked={isSecond} onPress={() => {
          setIsSecond(!isSecond)
          if (isAll) setIsAll(false)
        }} />
      </View>
      <Button type={isValidate() ? "fill" : "inactive"} radius={16} onPress={clickNextHandler}><YoYoText type="md" bold>확인</YoYoText></Button>
    </View>
  )
}
const styles = StyleSheet.create({
  container: {
    flex: 0.8,
    paddingVertical: 24,
    paddingHorizontal: 24,
    gap: 36
  },
  rowContainer: {
    paddingHorizontal: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',

  }
})