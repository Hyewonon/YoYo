import { View, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import { useNavigation } from '@react-navigation/native'

export default function EventListCard({ event }) {
  const navigation = useNavigation();
  function clickEventHandler() {
    navigation.navigate("EventDetail")
  }
  return (
    <Card height={96} onPress={clickEventHandler}>
      <View style={styles.container}>
        <View style={styles.innerContainer}>
          <YoYoText type="subTitle" bold>{event.name}</YoYoText>
          <YoYoText type="content">{event.date}</YoYoText>
        </View>
        <YoYoText type="desc">{event.position}</YoYoText>
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
  container: {
    gap: 12
  }
})