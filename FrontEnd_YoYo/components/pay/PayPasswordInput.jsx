import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { MainStyle } from '../../constants/style'
import YoYoText from '../../constants/YoYoText'

export default function PayPasswordInput({ number }) {
  return (
    <View style={styles.container}>
      {number > -1 && <YoYoText type="md" bold>*</YoYoText>}
    </View>
  )
}
const styles = StyleSheet.create({
  container: {
    width: 40,
    height: 40,
    borderWidth: 2,
    borderColor: MainStyle.colors.main,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 8
  }
})