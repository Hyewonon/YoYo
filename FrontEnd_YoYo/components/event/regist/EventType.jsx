import { View, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import Button from '../../common/Button';
import YoYoText from '../../../constants/YoYoText';

export default function EventType({ setIsActive }) {
  const [selectedType, setSelectedType] = useState("");
  function clickTypeHandler(type) {
    if (selectedType === type) {
      setSelectedType("")
      setIsActive(false)
    }
    else {
      setIsActive(true)
      setSelectedType(type)
    }
  }
  return (
    <View style={styles.container}>
      <Button type={selectedType
        === "결혼" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("결혼")}><YoYoText type="desc" bold>결혼</YoYoText></Button>
      <Button type={selectedType
        === "장례식" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("장례식")}><YoYoText type="desc" bold>장례식</YoYoText></Button>
      <Button type={selectedType
        === "기타" ? "hover" : "normal"} width={96} radius={16} onPress={() => clickTypeHandler("기타")}><YoYoText type="desc" bold>기타</YoYoText></Button>
    </View >
  )
}
const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    gap: 24,
  }
})