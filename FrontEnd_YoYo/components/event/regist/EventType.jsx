import { View, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import Button from '../../common/Button';
import YoYoText from '../../../constants/YoYoText';

export default function EventType({ setIsActive, setEvent, event }) {
  function clickTypeHandler(type) {
    if (event.eventType === type) {
      setEvent((prev) => ({
        ...prev,
        eventType: ""
      }))
      setIsActive(false)
    }
    else {
      setIsActive(true)
      setEvent((prev) => ({
        ...prev,
        eventType: type
      }))
    }
  }
  return (
    <View style={styles.container}>
      <Button type={event.eventType
        === "WEDDING" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("WEDDING")}><YoYoText type="desc" bold>결혼</YoYoText></Button>
      <Button type={event.eventType
        === "FUNERAL" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("FUNERAL")}><YoYoText type="desc" bold>장례식</YoYoText></Button>
      <Button type={event.eventType
        === "OTHERS" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("OTHERS")}><YoYoText type="desc" bold>기타</YoYoText></Button>
    </View >
  )
}
const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    gap: 24,
  }
})