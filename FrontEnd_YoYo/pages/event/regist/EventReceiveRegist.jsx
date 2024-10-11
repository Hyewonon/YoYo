import { View, Text, Animated, Modal, Pressable, Alert } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../../../components/common/Container'
import { getRelations } from '../../../apis/https/relationApi'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import Input from '../../../components/common/Input'
import TagList from '../../../components/common/TagList'
import SelectModal from '../../../components/ocr/SelectModal'
import Button from '../../../components/common/Button'
import { postTransaction } from '../../../apis/https/transactionApi'
// {
//   "transactionType": "SEND",
//   "memberId": 2,
//   "name": "김현태",
//   "relationType": "friend",
//   "description": "친구 이찬진 <-> 김현태",
//   "eventId": 0,
//   "eventName": "금곡결혼식",
//   "amount": 50000,
//   "memo": "결혼 축하한다."
// }
export default function EventReceiveRegist({ route, navigation }) {
  const event = route.params.event
  const [friend, setFriend] = useState({
    "transactionType": "RECEIVE",
    "memberId": 0,
    "name": "",
    "relationType": "",
    "description": "",
    "eventId": event.eventId,
    "eventName": event.title,
    "amount": "",
    "memo": ""
  })

  const [friends, setFriends] = useState()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const animation = useRef(new Animated.Value(0)).current
  function isButtonOpen() {
    return friend.name.length > 0 && friend.amount > 0 && friend.description.length > 0
  }
  useEffect(() => {
    if (isButtonOpen()) {
      Animated.timing(animation, {
        toValue: 1,
        duration: 300,
        useNativeDriver: true,
      }).start()
    } else {
      Animated.timing(animation, {
        toValue: 0,
        duration: 300,
        useNativeDriver: true,
      }).start();
    }
  }, [friend])
  function clickTag(type) {
    setFriend((prev) => ({
      ...prev,
      relationType: type.toUpperCase()
    }))
  }
  // 이름 변경 핸들러
  function handleNameChange(text) {
    setFriend((prev) => ({
      ...prev,
      name: text
    }))
  }

  // 금액 변경 핸들러
  function handleMoneyChange(text) {
    const parsedMoney = parseInt(text, 10) || undefined; // 숫자로 변환, 숫자가 아니면 0
    setFriend((prev) => ({
      ...prev,
      amount: parsedMoney
    }))
  }

  // 관계설명 변경 핸들러
  function handleDescriptionChange(text) {
    setFriend((prev) => ({
      ...prev,
      description: text
    }))
  }
  // 관계설명 변경 핸들러
  function handleMemoChange(text) {
    setFriend((prev) => ({
      ...prev,
      memo: text
    }))
  }
  //이름 변경할때마다 친구 리스트 가져오기

  useEffect(() => {

    async function fetchFriends() {
      if (friend.name.length > 0) {
        try {
          const response = await getRelations(friend.name)
          if (response && response.length > 0) {
            const tmpData = response.map((item, idx) => ({
              id: idx,
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
          } else {
            setFriends()
          }
        } catch (error) {
          console.log(error)
        }
      } else {
        setFriends()
      }
    }
    fetchFriends()
  }, [friend.name])
  async function handleClickRegist() {
    try {
      await postTransaction(friend)
      Alert.alert("등록 완료", "거래내역이 추가되었습니다.", [{ text: "확인", onPress: () => navigation.navigate("EventDetail", { id: event.eventId }) }])
    } catch (error) {
      Alert.alert("등록 실패", "입력값을 확인해주세요.")
    }

  }

  return (
    <>
      <Container>
        <YoYoText type="md" bold color={MainStyle.colors.main}>이름</YoYoText>
        <Input text={friend.name} type="normal" isError={friend.name.length === 0} onChange={handleNameChange} errorMessage="값을 입력해주세요." placeholder="이름을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>금액</YoYoText>
        <Input text={friend.amount && friend.amount.toString()} type="phoneNumber" isError={friend.amount === 0} onChange={handleMoneyChange} errorMessage="값을 입력해주세요." placeholder="금액을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>관계 설명</YoYoText>
        <Input text={friend.description} type="normal" isError={friend.description.length === 0} onChange={handleDescriptionChange} errorMessage="값을 입력해주세요." placeholder="관계 설명을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>관계</YoYoText>
        <TagList onPress={clickTag} selectedTag={friend.relationType.toLowerCase()} size={84} />
        <YoYoText type="md" bold color={MainStyle.colors.main}>특이사항</YoYoText>
        <Input text={friend.memo} type="normal" isError={friend.memo.length === 0} onChange={handleMemoChange} errorMessage="값을 입력해주세요." placeholder="특이사항을 입력해주세요." />
        {friends && friends.length > 0 && <Pressable onPress={() => setIsModalOpen(true)}>
          <YoYoText type="md" bold color={MainStyle.colors.red}>※지인 목록에서 선택하기</YoYoText>
        </Pressable>}
      </Container>
      {isButtonOpen() &&
        <Animated.View style={{
          opacity: animation,
          transform: [{ scale: animation }]
        }}>
          <Button type="fill" width="100%" radius={0} onPress={handleClickRegist}><YoYoText type="md" bold>등록</YoYoText></Button>
        </Animated.View>}
      <Modal visible={isModalOpen} animationType='fade'
        transparent={true}
        onRequestClose={() => setIsModalOpen(false)}>
        <SelectModal type="event" onPress={() => setIsModalOpen(false)} friend={friend} setFriend={setFriend} data={friends} />
      </Modal>
    </>
  )
}