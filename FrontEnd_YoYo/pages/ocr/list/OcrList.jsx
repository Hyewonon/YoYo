import { View, StyleSheet, FlatList, Alert } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import OcrCard from '../../../components/card/Event/OcrCard'
import Button from '../../../components/common/Button'
import { useSelector } from 'react-redux'
import { confirmOcr } from '../../../apis/https/transactionApi'
import AsyncStorage from '@react-native-async-storage/async-storage'

export default function OcrList({ navigation }) {
  const ocrData = useSelector((state) => state.ocr.ocrData)
  function clickCardHandler(item) {
    navigation.navigate("OCRSELECT", {
      idx: item.id
    })
  }
  async function handleClickOk() {
    try {
      const eventId = await AsyncStorage.getItem("eventId")
      const sendData = ocrData.map((item) => ({
        name: item.name,
        memberId: item.oppositeId,
        relationType: item.relationType,
        amount: item.amount,
        description: item.description
      }))
      const response = await confirmOcr(eventId, sendData)
      Alert.alert("등록 완료!", "명단 등록이 완료되었습니다.", [{
        text: "확인",
        onPress: () => navigation.navigate("EventDetail", { id: eventId })
      }])

    } catch (error) {
      console.log("OCR전송 오류", error)
    }
  }
  return (
    <>
      <Container>
        <View style={styles.topContainer}>
          <YoYoText type="md">이름</YoYoText>
          <YoYoText type="md">금액</YoYoText>
          <YoYoText type="md">비고</YoYoText>
          <YoYoText type="md">태그</YoYoText>
        </View>
        <FlatList renderItem={({ item }) => <OcrCard data={item} onPress={() => clickCardHandler(item)} />} data={ocrData} keyExtractor={(item) => item.id + " ocr"} />


      </Container>
      <Button type="fill" width="100%" radius={0} onPress={handleClickOk}><YoYoText type="md" bold>명단 확정</YoYoText></Button>
    </>
  )
}
const styles = StyleSheet.create({
  topContainer: {
    borderWidth: 1,
    borderRadius: 24,
    borderColor: MainStyle.colors.main,
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    padding: 8
  }
})