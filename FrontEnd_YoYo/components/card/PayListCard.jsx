import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Card from './Card';
import YoYoText from '../../constants/YoYoText';
import { MainStyle } from '../../constants/style';

export default function PayListCard({ data, type }) {
  return (
    <Card height={96}>
      <View style={styles.rowContainer}>
        <YoYoText type="md" bold>{data.title}</YoYoText>
        <YoYoText type="content">{data.date}</YoYoText>
      </View>
      <View style={styles.moneyContainer}>
        <YoYoText type="subTitle" bold color={type ? MainStyle.colors.main : MainStyle.colors.red}>{data.money}원</YoYoText>
      </View>
    </Card>
  )
}
const styles = StyleSheet.create({
  rowContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start'
  },
  moneyContainer: {
    justifyContent: "flex-end",
    alignItems: 'flex-end'
  }
})
