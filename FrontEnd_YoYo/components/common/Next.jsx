import { Pressable } from 'react-native'
import YoYoText from '../../constants/YoYoText'
import { MainStyle } from '../../constants/style'

export default function Next({ isActive, onPress, final }) {
  return (
    <Pressable onPress={onPress}>
      <YoYoText type="subTitle" bold color={isActive ? MainStyle.colors.black : MainStyle.colors.lightGray} >{final ? "완료" : "다음"}</YoYoText>
    </Pressable>
  )
}