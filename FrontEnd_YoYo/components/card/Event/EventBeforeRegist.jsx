import { View, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import { formatDate } from '../../../util/date'
export default function EventBeforeRegist({ event, onPress }) {
  return (
    <Card height={96} onPress={onPress}>
      <View style={styles.container}>
        <View style={styles.innerContainer}>
          <YoYoText type="subTitle" bold>{event.senderName}</YoYoText>
          <YoYoText type="content">{formatDate(event.time)}</YoYoText>
        </View>
        <View style={styles.innerContainer}>
          <YoYoText type="desc" bold>{event.memo}</YoYoText>
          <YoYoText type="md" bold color={MainStyle.colors.main}>{event.amount}원</YoYoText>
        </View>
      </View>
    </Card>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start'
  },
  innerContainer2: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-end'
  },
  container: {
    gap: 12
  }
})