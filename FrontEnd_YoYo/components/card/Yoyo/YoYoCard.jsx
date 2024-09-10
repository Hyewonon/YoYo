import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import Tag from '../../common/Tag';
import MoneyGauge from './MoneyGauge';
import { useNavigation } from '@react-navigation/native';

export default function YoYoCard({ type }) {
  const navigation = useNavigation();
  function clickFriendHandler() {
    navigation.navigate("YoYoDetail")
  }
  return (
    <Card height={128} onPress={clickFriendHandler}>
      <View style={styles.innerContainer}>
        <View>
          <YoYoText type="subTitle" bold>김현태</YoYoText>
          <YoYoText type="content">고등학교 친구</YoYoText>
        </View>
        <Tag type="friend" width={88}>친구</Tag>
      </View>
      <MoneyGauge give={20000} take={12000} />
    </Card>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: "100%",
    alignItems: "flex-start"
  }
})