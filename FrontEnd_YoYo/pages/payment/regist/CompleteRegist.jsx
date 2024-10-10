import { View, Image, StyleSheet } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import Button from '../../../components/common/Button'

export default function CompleteRegist({ bank, accountNumber, onPress }) {
  return (
    <>
      <Container>
        <View style={styles.container}>
          <YoYoText type="subTitle" center bold>계좌 등록이 완료되었습니다.</YoYoText>
          <View style={styles.innerContainer}>
            <YoYoText type="subTitle" bold>계좌 정보</YoYoText>
            <View style={styles.rowContainer}>
              <Image source={bank} />
              <YoYoText type='subTitle'>{accountNumber}</YoYoText>
            </View>
          </View>
        </View>
      </Container>
      <Button type="fill" width="100%" radius={16} onPress={onPress}><YoYoText type="md" bold>결제 비밀번호 등록</YoYoText></Button>
    </>
  )
}
const styles = StyleSheet.create({
  container: {
    gap: 36,
  },
  rowContainer: {
    flexDirection: 'row',
    gap: 8,
    alignItems: 'center'
  },
  innerContainer: {
    gap: 16
  }
})