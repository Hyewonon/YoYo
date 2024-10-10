import { Animated } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import { MainStyle } from '../../../constants/style'
import Input from '../../../components/common/Input'
import Button from '../../../components/common/Button'
import TagList from '../../../components/common/TagList'
export default function RegistNewFriend({ route }) {
  const friend = route.params.friend
  const [name, setName] = useState(friend.name);
  const [tag, setTag] = useState(friend.tag);
  const [memo, setMemo] = useState(friend.detail)
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
          <Button type="fill" width="100%" radius={0}><YoYoText type="md" bold>등록</YoYoText></Button>
        </Animated.View>}
    </>
  )
}