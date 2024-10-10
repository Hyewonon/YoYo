import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import Tag from '../../common/Tag';
import { useNavigation } from '@react-navigation/native';
import { MainStyle } from '../../../constants/style';

export default function EventAfterRegist({ event }) {
  const navigation = useNavigation();
  function clickFriendHandler() {
    navigation.navigate("YoYoDetail")
  }
  return (
    <View style={styles.cardContainer}>
      <View style={styles.innerContainer}>
        <View>
          <YoYoText type="subTitle" bold>{event.name}</YoYoText>
          <YoYoText type="content">{event.detail}</YoYoText>
        </View>
        <Tag type={event.tag} width={88}>친구</Tag>
      </View>
      <View style={styles.innerContainer2}>
        <YoYoText type="desc" bold>{event.date}</YoYoText>
        <YoYoText type="subTitle" color={MainStyle.colors.main} bold>{event.price}원</YoYoText>
      </View>

    </View>
  )
}
const styles = StyleSheet.create({
  cardContainer: {
    borderRadius: 16,
    overflow: "hidden",
    marginVertical: 8,
    borderWidth: 2,
    borderColor: MainStyle.colors.main,
    padding: 12
  },
  innerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: "100%",
    alignItems: "flex-start"
  }
  , innerContainer2: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: "100%",
    alignItems: "flex-end"
  }
})