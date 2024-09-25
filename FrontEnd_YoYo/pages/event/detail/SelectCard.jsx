import { View, Text, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoCard from '../../../components/card/Yoyo/YoYoCard'
import { FlatList } from 'react-native-gesture-handler'
import Button from '../../../components/common/Button'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
const events = [
  // { id: 1, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 2, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 3, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 4, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 5, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 6, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 7, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },
  // { id: 8, name: "이찬진", tag: "friend", detail: "고등학교 친구", date: "2024.09.11", price: 50000 },

]
export default function SelectCard({ navigation, route }) {
  const friend = route.params.friend;
  const [selectedCard, setSelectedCard] = useState(-1);
  function clickBottomButtonHandler() {
    if (selectedCard === -1) {
      navigation.navigate("지인추가", { friend: friend })
    } else {
      alert("포스트 요청")
    }
  }
  function clickCard(id) {
    if (id === selectedCard) {
      setSelectedCard(-1)
    } else {
      setSelectedCard(id);

    }
  }
  function renderedItem(item) {
    return <YoYoCard type="select" onPress={() => clickCard(item.item.id)} item={item.item} selectedCard={selectedCard} />
  }
  return (
    <>
      <View style={styles.topContainer}>
        <YoYoText type="subTitle" color={MainStyle.colors.main} bold>선택된 지인을 매칭해주세요.</YoYoText>
        <YoYoText type="md">마음 전달 내역 중 동일 인물로 판단되는 {" "}지인을 선택해주세요.</YoYoText>
        <YoYoText type="content" bold color={MainStyle.colors.error}>※직접 추가도 가능합니다!</YoYoText>
        <View style={styles.innerContainer}>
          <View style={styles.rowContainer}>
            <YoYoText type="md" bold color={MainStyle.colors.main}>이름</YoYoText>
            <YoYoText type="md" bold>{friend.name}</YoYoText>
          </View>
          <View style={styles.rowContainer}>
            <YoYoText type='md' bold color={MainStyle.colors.main}>메모</YoYoText>
            <YoYoText type="md" bold>{friend.detail}</YoYoText>
          </View>
        </View>
      </View>
      <Container>

        <View style={{ flex: 1 }}>
          {events && events.length > 0 ? <FlatList data={events} renderItem={renderedItem} key={item => item.id} /> : <YoYoText type="md" bold color={MainStyle.colors.main} center>동명의 지인이 존재하지 않습니다.</YoYoText>}
        </View>

      </Container>
      <Button radius={0} width="100%" type="fill" onPress={clickBottomButtonHandler}><YoYoText type="md" bold>{selectedCard === -1 ? "직접 추가하기" : "확정"}</YoYoText></Button>
    </>
  )
}
const styles = StyleSheet.create({
  topContainer: {
    padding: 24,
    backgroundColor: MainStyle.colors.hover
  },
  innerContainer: {
    backgroundColor: MainStyle.colors.white,
    padding: 12,
    justifyContent: 'center',
    marginTop: 8,
    borderRadius: 16
  },
  rowContainer: {
    flexDirection: 'row',
    gap: 8,
    alignItems: 'center',
  }
})