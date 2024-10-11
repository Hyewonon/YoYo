import { View, Text, StyleSheet } from 'react-native'
import React, { useEffect, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoCard from '../../../components/card/Yoyo/YoYoCard'
import { FlatList } from 'react-native-gesture-handler'
import Button from '../../../components/common/Button'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import { getRelations } from '../../../apis/https/realtionApi'
import { updateTransaction } from '../../../apis/https/transactionApi'
import { Alert } from 'react-native'
export default function SelectCard({ navigation, route }) {
  const [friends, setFriends] = useState();
  const { friend, eventId } = route.params;
  const [selectedCard, setSelectedCard] = useState(-1);
  const [selectedFriend, setSelectedFriend] = useState({
    relationId: "",
    oppositeId: "",
    name: "",
    relationType: "",
    description: "",
    amount: ""
  })
  async function modifyTransaction() {
    try {
      await updateTransaction(friend.transactionId, selectedFriend)
    } catch (error) {
      console.log(error)
    }
  }
  function clickBottomButtonHandler() {
    if (selectedCard === -1) {
      navigation.navigate("지인추가", { friend: friend, id: eventId })
    } else {
      modifyTransaction()
      Alert.alert("등록 완료", "지인 추가가 완료되었습니다.", [{ text: "확인", onPress: () => navigation.navigate("EventDetail", { id: eventId }) }])
    }
  }
  function clickCard(item) {
    if (item.id === selectedCard) {
      setSelectedCard(-1)
    } else {
      setSelectedCard(item.id);
      setSelectedFriend((prev) => ({
        ...prev,
        relationId: item.relationId,
        oppositeId: item.oppositeId,
        name: item.name,
        relationType: item.type.toUpperCase(),
        description: item.description,
        amount: friend.amount
      }))

    }
  }
  function renderedItem({ item }) {
    return <YoYoCard type="select" onPress={() => clickCard(item)} data={item} selectedCard={selectedCard} />
  }
  async function fetchFriends() {
    try {
      const response = await getRelations(friend.senderName)
      const tmpData = response.map((item) => ({
        id: item.oppositeId,
        oppositeId: item.oppositeId,
        relationId: item.relationId,
        name: item.oppositeName,
        title: item.oppositeName,
        description: item.description,
        type: item.relationType.toLowerCase(),
        give: item.totalReceivedAmount,
        take: item.totalSentAmount,
      }));
      setFriends(tmpData)
    } catch (error) {
      console.log(error)
    }
  }
  useEffect(() => {
    fetchFriends()
  }, [])
  return (
    <>
      <View style={styles.topContainer}>
        <YoYoText type="subTitle" color={MainStyle.colors.main} bold>선택된 지인을 매칭해주세요.</YoYoText>
        <YoYoText type="md">마음 전달 내역 중 동일 인물로 판단되는 {" "}지인을 선택해주세요.</YoYoText>
        <YoYoText type="content" bold color={MainStyle.colors.error}>※직접 추가도 가능합니다!</YoYoText>
        <View style={styles.innerContainer}>
          <View style={styles.rowContainer}>
            <YoYoText type="md" bold color={MainStyle.colors.main}>이름</YoYoText>
            <YoYoText type="md" bold>{friend.senderName}</YoYoText>
          </View>
          <View style={styles.rowContainer}>
            <YoYoText type='md' bold color={MainStyle.colors.main}>메모</YoYoText>
            <YoYoText type="md" bold>{friend.memo}</YoYoText>
          </View>
        </View>
      </View>
      <Container>

        <View style={{ flex: 1 }}>
          {friends && friends.length > 0 ? <FlatList data={friends} renderItem={renderedItem} key={item => item.transactionId} /> : <YoYoText type="md" bold color={MainStyle.colors.main} center>동명의 지인이 존재하지 않습니다.</YoYoText>}
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