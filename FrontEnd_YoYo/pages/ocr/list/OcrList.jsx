import { View, StyleSheet, FlatList } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import OcrCard from '../../../components/card/Event/OcrCard'
import Button from '../../../components/common/Button'
const data = [{
  id: 1,
  name: "이찬진",
  money: 50000,
  memo: "라이벌",
  tagType: 'friend',
  tagName: '친구',
  isDuplicate: false
}, {
  id: 2,
  name: "이찬진",
  money: 50000,
  memo: "라이벌",
  tagType: 'friend',
  tagName: '친구',
  isDuplicate: true
}]
export default function OcrList({ navigation }) {
  function clickCardHandler(item) {
    navigation.navigate("OCRSELECT", {
      data: item
    })
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
        <FlatList renderItem={({ item }) => <OcrCard data={item} onPress={item.isDuplicate ? () => clickCardHandler(item) : undefined} />} data={data} keyExtractor={(item) => item.id} />


      </Container>
      <Button type="fill" width="100%" radius={0}><YoYoText type="md" bold>명단 확정</YoYoText></Button>
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