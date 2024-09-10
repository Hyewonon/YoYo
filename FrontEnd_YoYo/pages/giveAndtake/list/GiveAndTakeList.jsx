import { View, Text } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoCard from '../../../components/card/Yoyo/YoYoCard';
import YoYoCardDetail from '../../../components/card/Yoyo/YoYoCardDetail';

export default function GiveAndTakeList() {
  return (
    <Container>
      <YoYoCard />
      <YoYoCardDetail />
    </Container>
  )
}