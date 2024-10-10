import { View, Text, Animated, Pressable, Modal } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import TagList from '../../../components/common/TagList'
import Input from '../../../components/common/Input'
import Button from '../../../components/common/Button'
import SelectModal from '../../../components/ocr/SelectModal'
// {
//   id: 2,
//   name: "이찬진",
//   money: 50000,
//   memo: "라이벌",
//   tagType: 'friend',
//   tagName: '친구',
//   isDuplicate: true
// }
export default function OcrSelect({ route }) {
  const [friend, setFriend] = useState(route.params.data)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const animation = useRef(new Animated.Value(0)).current
  function isButtonOpen() {
    return friend.name.length > 0 && friend.money > 0 && friend.memo.length > 0
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
      tagType: type
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
      money: parsedMoney
    }))
  }

  // 메모 변경 핸들러
  function handleMemoChange(text) {
    setFriend((prev) => ({
      ...prev,
      memo: text
    }))
  }
  return (
    <>
      <Container>
        <YoYoText type="md" bold color={MainStyle.colors.main}>이름</YoYoText>
        <Input text={friend.name} type="normal" isError={friend.name.length === 0} onChange={handleNameChange} errorMessage="값을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>금액</YoYoText>
        <Input text={friend.money && friend.money.toString()} type="phoneNumber" isError={friend.money === 0} onChange={handleMoneyChange} errorMessage="값을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>비고</YoYoText>
        <Input text={friend.memo} type="normal" isError={friend.memo.length === 0} onChange={handleMemoChange} errorMessage="값을 입력해주세요." />
        <YoYoText type="md" bold color={MainStyle.colors.main}>관계</YoYoText>
        <TagList onPress={clickTag} selectedTag={friend.tagType} size={84} />
        <Pressable onPress={() => setIsModalOpen(true)}>
          <YoYoText type="md" bold color={MainStyle.colors.red}>※지인 목록에서 선택하기</YoYoText>
        </Pressable>
      </Container>
      {isButtonOpen() &&
        <Animated.View style={{
          opacity: animation,
          transform: [{ scale: animation }]
        }}>
          <Button type="fill" width="100%" radius={0}><YoYoText type="md" bold>등록</YoYoText></Button>
        </Animated.View>}
      <Modal visible={isModalOpen} animationType='fade'
        transparent={true}
        onRequestClose={() => setIsModalOpen(false)}>
        <SelectModal onPress={() => setIsModalOpen(false)} />
      </Modal>
    </>
  )
}