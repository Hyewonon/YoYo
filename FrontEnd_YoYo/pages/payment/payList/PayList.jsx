import { View, Text, StyleSheet, FlatList } from 'react-native'
import React, { useEffect, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import MoneyCard from '../../../components/card/mainPage/MoneyCard'
import SelectTap from '../../../components/common/SelectTap'
import { MainStyle } from '../../../constants/style'
import PayListCard from '../../../components/card/PayListCard'
import { getPayList } from '../../../apis/https/payApi'
function renderedItem(item, type) {
  return <PayListCard data={item.item} type={type} />
}
export default function PayList({ route }) {
  const payInfo = route.params.payInfo
  const [isIncome, setIsIncome] = useState(true)
  const [payList, setPayList] = useState()
  async function fetchPayList(type) {
    try {
      const response = await getPayList(type)
      setPayList(response)
    } catch (error) {
      console.log(error)
    }
  }
  useEffect(() => {
    if (isIncome) fetchPayList('DEPOSIT')
    else fetchPayList('WITHDRAW')
  }, [isIncome])
  return (
    <Container>
      <View style={styles.innerContainer}>
        <YoYoText type="md" bold>{payInfo.memberName}님의 페이 내역</YoYoText>
        <MoneyCard account={payInfo} money={payInfo.balance} />
        <SelectTap left="입금" right="출금" leftColor={MainStyle.colors.main} rightColor={MainStyle.colors.main} stateHandler={setIsIncome} />
      </View>
      {payList &&
        <FlatList data={payList} renderItem={(item) => renderedItem(item, isIncome)} keyExtractor={(item) => (item.id ? item.id.toString() + "paylist" : Math.random().toString() + "paylist")} style={{ marginTop: 24 }} />}
    </Container>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    gap: 24
  }
})