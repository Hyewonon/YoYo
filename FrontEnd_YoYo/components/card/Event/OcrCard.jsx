import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import { Ionicons } from '@expo/vector-icons';
import Tag from '../../common/Tag';
import { MainStyle } from '../../../constants/style';

export default function OcrCard({ data, onPress }) {
  return (
    <Card height={64} onPress={onPress}>
      <View style={styles.innerContainer}>
        <View style={styles.rowContainer}>
          <YoYoText type="content">{data.name}</YoYoText>
          <Ionicons name={data.isDuplicate ? "alert-circle" : "checkmark-circle"} size={18} color={data.isDuplicate ? MainStyle.colors.red : MainStyle.colors.green} />
        </View>
        <YoYoText type="content">{data.money}원</YoYoText>
        <YoYoText type="content">{data.memo}</YoYoText>
        <Tag type={data.tagType} width={80}>{data.tagName} </Tag>
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