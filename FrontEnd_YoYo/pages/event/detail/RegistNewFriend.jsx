import { Alert, Animated } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import Input from '../../../components/common/Input'
import Button from '../../../components/common/Button'
import TagList from '../../../components/common/TagList'
import { updateTransaction } from '../../../apis/https/transactionApi'
export default function RegistNewFriend({ route, navigation }) {
  const { friend, id } = route.params
  const [name, setName] = useState(friend.senderName);
  const [tag, setTag] = useState(friend.relationType.toLowerCase());
  const [memo, setMemo] = useState(friend.memo)
  const animation = useRef(new Animated.Value(0)).current
  function isButtonOpen() {
    return name.length > 0 && tag.length > 0 && memo.length > 0
  }
  function clickTag(type) {
    setTag(type)
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
  }, [name, tag, memo])
  async function clickeRegistHandler() {
    try {
      await updateTransaction(friend.transactionId, { relationId: null, oppositeId: friend.oppositeId, name: name, relationType: tag.toUpperCase(), description: memo, amount: friend.amount })
      Alert.alert("등록 완료", "지인 추가가 완료되었습니다.", [{
        text: '확인',
        onPress: () => {
          navigation.navigate("EventDetail", { id: id })
        }
      }])
    } catch (error) {
      console.log("에러", error)
    }
  }
  return (
    <>
      <Container>
        <YoYoText type="subTitle" bold color={MainStyle.colors.main}>이름 입력</YoYoText>
        <Input onChange={setName} text={name} isError={name.length === 0} placeholder="이름을 입력해주세요" type="default" />
        <YoYoText type="subTitle" bold color={MainStyle.colors.main}>태그 선택</YoYoText>
        <TagList onPress={clickTag} selectedTag={tag} size={88} />
        <YoYoText type="subTitle" bold color={MainStyle.colors.main}>메모</YoYoText>
        <Input onChange={setMemo} text={memo} isError={memo.length === 0} placeholder="지인이 알아볼 수 있는 내용을 입력해주세요." type="default" />
      </Container >
      {isButtonOpen() &&
        <Animated.View style={{
          opacity: animation,
          transform: [{ scale: animation }]
        }}>
          <Button type="fill" width="100%" radius={0} onPress={clickeRegistHandler}><YoYoText type="md" bold>등록</YoYoText></Button>
        </Animated.View>}
    </>
  )
}