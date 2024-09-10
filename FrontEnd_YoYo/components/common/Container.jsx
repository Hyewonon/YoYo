import { View } from 'react-native'
import React from 'react'

export default function Container({ children }) {
  return (
    <View style={{
      padding: 24,
      backgroundColor: 'white',
      flex: 1
    }}>
      {children}
    </View>
  )
}