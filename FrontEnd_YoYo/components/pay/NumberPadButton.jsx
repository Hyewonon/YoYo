import { View, Text, Pressable, StyleSheet } from 'react-native'
import React from 'react'
import YoYoText from '../../constants/YoYoText'
import { MainStyle } from '../../constants/style'

export default function NumberPadButton({ number, onPress }) {
  return (
    <Pressable onPress={onPress} style={({ pressed }) => [styles.container, pressed && styles.pressed]}>
      <YoYoText color="white" bold center type="subTitle">{number}</YoYoText>
    </Pressable>
  )
}
styles = StyleSheet.create({
  container: {
    width: "33%",
    height: "33%",
    justifyContent: 'center',
    alignItems: 'center'
  },
  pressed: {
    backgroundColor: MainStyle.colors.hover
  }
})