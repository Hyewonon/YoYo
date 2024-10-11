import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import Tag from '../../common/Tag';
const tag = {
  friend: "친구",
  family: "가족",
  company: "직장",
  etc: "기타"
}
export default function OcrCard({ data, onPress }) {
  return (
    <Card height={64} onPress={onPress}>
      <View style={styles.innerContainer}>
        <YoYoText type="content" bold>{data.name}</YoYoText>
        <YoYoText type="content">{data.amount}원</YoYoText>
        <YoYoText type="content">{data.description}</YoYoText>
        <Tag type={data.relationType.toLowerCase()} width={80}>{tag[data.relationType.toLowerCase()]} </Tag>
      </View>
    </Card>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center'
  },
  rowContainer: {
    flexDirection: 'row',
    gap: 4,
    alignItems: 'center'
  }
})