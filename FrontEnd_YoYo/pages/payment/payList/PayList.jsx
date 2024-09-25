import { View, Text, StyleSheet, FlatList } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import MoneyCard from '../../../components/card/mainPage/MoneyCard'
import SelectTap from '../../../components/common/SelectTap'
import { MainStyle } from '../../../constants/style'
import PayListCard from '../../../components/card/PayListCard'
const datas = [{
  id: 1,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 2,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 3,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 4,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 5,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 6,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 7,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
},
{
  id: 8,
  title: "이택근",
  date: "2024.08.29",
  money: 50000
}]
function renderedItem(item, type) {
  return <PayListCard data={item.item} type={type} />
}
export default function PayList() {
  const [isIncome, setIsIncome] = useState(true)
  return (
    <Container>
      <View style={styles.innerContainer}>
        <YoYoText type="md" bold>이름님의 페이 내역</YoYoText>
        <MoneyCard />
        <SelectTap left="입금" right="출금" leftColor={MainStyle.colors.main} rightColor={MainStyle.colors.main} stateHandler={setIsIncome} />
      </View>
      <FlatList data={datas} renderItem={(item) => renderedItem(item, isIncome)} keyExtractor={(item) => item.id} style={{ marginTop: 24 }} />
    </Container>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    gap: 24
  }
})