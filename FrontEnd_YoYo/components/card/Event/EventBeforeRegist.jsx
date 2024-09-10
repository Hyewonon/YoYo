import { View, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import { useNavigation } from '@react-navigation/native'
import { MainStyle } from '../../../constants/style'
export default function EventBeforeRegist({ event }) {
  const navigation = useNavigation();
  function clickFriendHandler() {
    navigation.navigate("")
  }
  return (
    <Card height={96} onPress={clickFriendHandler}>
      <View style={styles.container}>
        <View style={styles.innerContainer}>
          <YoYoText type="subTitle" bold>{event.name}</YoYoText>
          <YoYoText type="content">{event.date}</YoYoText>
        </View>
        <View style={styles.innerContainer}>
          <YoYoText type="desc" bold>{event.detail}</YoYoText>
          <YoYoText type="md" bold color={MainStyle.colors.main}>{event.price}원</YoYoText>
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