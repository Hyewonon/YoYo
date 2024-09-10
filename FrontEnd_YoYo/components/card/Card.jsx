import { View, Pressable, StyleSheet } from 'react-native'
import React from 'react'
import { MainStyle } from '../../constants/style'

export default function Card({ children, onPress, height }) {
  return (
    <View style={styles.cardContainer}>
      <Pressable
        onPress={onPress}
        style={({ pressed }) => [
          styles.pressableContainer,
          pressed && styles.pressed, { height: height }
        ]}
        android_ripple={{ color: MainStyle.colors.hover }}
      >
        <View style={[styles.container, { height: height }]}>
          {children}
        </View>
      </Pressable>
    </View >
  )
}
const styles = StyleSheet.create({
  cardContainer: {
    borderRadius: 16,
    overflow: "hidden",
    marginBottom: 24,
    elevation: 5,
    backgroundColor: "white",
  },
  pressableContainer: {
    borderRadius: 16,
  },
  container: {
    borderRadius: 16,
    borderWidth: 2,
    borderColor: MainStyle.colors.main,
    padding: 12
  },
  pressed: {
    opacity: 0.5,
  },
})