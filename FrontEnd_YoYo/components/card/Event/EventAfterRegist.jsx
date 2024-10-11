import { View, Text, StyleSheet, Pressable } from 'react-native'
import React from 'react'
import Card from '../Card'
import YoYoText from '../../../constants/YoYoText'
import Tag from '../../common/Tag';
import { useNavigation } from '@react-navigation/native';
import { MainStyle } from '../../../constants/style';
import { formatDate } from './../../../util/date';
const tagTranslate = {
  all: "전체",
  friend: "친구",
  family: "가족",
  company: "직장",
  etc: "기타",
};
export default function EventAfterRegist({ event }) {
  const navigation = useNavigation();
  function clickFriendHandler() {
    navigation.navigate("GiveAndTakeDetail", { id: event.oppositeId })
  }
  return (
    <View style={styles.cardContainer}>
      <Pressable style={styles.pressContainer} android_ripple={{ color: MainStyle.colors.hover }} onPress={clickFriendHandler}>
        <View style={styles.innerContainer}>
          <View>
            <YoYoText type="subTitle" bold>{event.senderName}</YoYoText>
            <YoYoText type="content">{event.memo}</YoYoText>
          </View>
          <Tag type={event.relationType.toLowerCase()} width={88}>{tagTranslate[event.relationType.toLowerCase()]}</Tag>
        </View>
        <View style={styles.innerContainer2}>
          <YoYoText type="desc" bold>{formatDate(event.time)}</YoYoText>
          <YoYoText type="subTitle" color={MainStyle.colors.main} bold>{event.amount}원</YoYoText>
        </View>

      </Pressable>
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

    overflow: 'hidden'
  },
  pressContainer: {
    padding: 12,
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