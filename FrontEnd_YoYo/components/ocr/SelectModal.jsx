import { View, Text, StyleSheet, FlatList, Animated } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import Container from '../common/Container'
import IconButton from './../common/IconButton';
import YoYoText from '../../constants/YoYoText';
import { MainStyle } from '../../constants/style';
import YoYoCard from '../card/Yoyo/YoYoCard';
import Button from '../common/Button';
const data = [
  { id: 0 },
  { id: 1 },
  { id: 2 },
  { id: 3 },
]
export default function SelectModal({ onPress }) {
  const [selectedCard, setSelectedCard] = useState(-1);
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
  function clickCard(id) {
    if (id === selectedCard) {
      setSelectedCard(-1)
    } else {
      setSelectedCard(id);
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
          <FlatList data={data} renderItem={({ item }) => <YoYoCard selectedCard={selectedCard} type="select" item={item} onPress={() => clickCard(item.id)} />} keyExtractor={(item) => item.id} />
        </View>
      </Container>
      {selectedCard > -1 &&
        <Animated.View style={{
          opacity: animation,
          transform: [{ scale: animation }]
        }}>
          <Button type="fill" width="100%" radius={0}><YoYoText type="md" bold>등록</YoYoText></Button>
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