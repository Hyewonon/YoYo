import { View, Text, StyleSheet } from 'react-native'
import React, { useLayoutEffect, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import Input from '../../../components/common/Input'
import Button from '../../../components/common/Button'
const prices = [5000, 10000, 50000, 100000]
export default function SendMoney({ route, navigation }) {
  const [price, setPrice] = useState(0)
  const title = route.params.title
  useLayoutEffect(() => {
    navigation.setOptions({
      title: title
    })
  }, [navigation, route.params])
  function handlePriceChange(text) {
    const numericValue = parseInt(text.replace(/[^0-9]/g, ''), 10) || 0;
    setPrice(numericValue);
  }
  function clickNextHandler() {
    navigation.navigate("RegistPayPassword", {
      data: {
        title: `${title} 완료`,
        content: `${price}원이 ${title === "충전하기" ? "충전되었습니다" : "송금되었습니다"}.`,
        money: price
      },
      type: "pay"
    })
  }
  return (
    <>
      <Container>
        <YoYoText type="md" bold>금액을 입력해주세요.</YoYoText>
        <Input onChange={handlePriceChange} text={price > 0 && price.toString
          ()
        } placeholder="금액을 입력해주세요." type="phoneNumber" />
        <View style={styles.buttonContainer}>
          {prices.map((money) => <Button type="normal" width="22%" radius={24} key={money} onPress={() => setPrice(price + money)}>+{money}</Button>)}
        </View>
      </Container >
      <Button radius={0} width="100%" type={price > 0 ? "fill" : "inactive"} onPress={clickNextHandler}><YoYoText type="md" bold>확인</YoYoText></Button>
    </>
  )
}
const styles = StyleSheet.create({
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 24
  }
})