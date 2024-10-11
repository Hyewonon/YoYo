import { View, Text, StyleSheet, FlatList, Animated } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../common/Container'
import IconButton from './../common/IconButton';
import YoYoText from '../../constants/YoYoText';
import { MainStyle } from '../../constants/style';
import YoYoCard from '../card/Yoyo/YoYoCard';
import Button from '../common/Button';

export default function SelectModal({ data, onPress, friend, setFriend, type }) {
  const [selectedCard, setSelectedCard] = useState(-1);
  const prevFriend = friend
  const animation = useRef(new Animated.Value(0)).current
  useEffect(() => {
    if (selectedCard > -1) {
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
  }, [selectedCard])
  function clickCard(item) {
    if (item.id === selectedCard) {
      setSelectedCard(-1)
      if (type === "event") {
        setFriend((prev) => ({
          ...prev,
          name: prevFriend.name,
          description: prevFriend.description,
          relationType: prevFriend.relationType,
          memberId: prevFriend.oppositeId
        }))
      } else {
        setFriend((prev) => ({
          ...prev,
          name: prevFriend.name,
          description: prevFriend.description,
          relationType: prevFriend.relationType,
          oppositeId: prevFriend.oppositeId
        }))
      }

    } else {
      setSelectedCard(item.id);
      if (type === "event") {
        setFriend((prev) => ({
          ...prev,
          name: item.name,
          description: item.description,
          relationType: item.type.toUpperCase(),
          memberId: item.oppositeId
        }))
      } else {
        setFriend((prev) => ({
          ...prev,
          name: item.name,
          description: item.description,
          relationType: item.type.toUpperCase(),
          oppositeId: item.oppositeId
        }))
      }
    }
  }
  return (
    <>
      <Container>
        <View style={styles.buttonContainer}>
          <IconButton icon="close" size={24} onPress={onPress} />
        </View>
        <View style={styles.innerContainer}>
          <YoYoText type="md" bold color={MainStyle.colors.main}>아래 리스트에서 선택해주세요.</YoYoText>
          <FlatList data={data} renderItem={({ item }) => <YoYoCard selectedCard={selectedCard} type="select" data={item} onPress={() => clickCard(item)} />} keyExtractor={(item) => item.id} />
        </View>
      </Container>
      {selectedCard > -1 &&
        <Animated.View style={{
          opacity: animation,
          transform: [{ scale: animation }]
        }}>
          <Button type="fill" width="100%" radius={0} onPress={onPress}><YoYoText type="md" bold >등록</YoYoText></Button>
        </Animated.View>}
    </>
  )
}
const styles = StyleSheet.create({
  buttonContainer: {
    alignItems: 'flex-end',
    justifyContent: 'flex-end'
  },
  innerContainer: {
    marginTop: 24,
    gap: 16
  }
})