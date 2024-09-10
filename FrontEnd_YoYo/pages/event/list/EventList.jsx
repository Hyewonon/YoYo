import { View, Text } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import EventListCard from './../../../components/card/Event/EventListCard';
import EventBeforeRegist from '../../../components/card/Event/EventBeforeRegist';
import EventAfterRegist from './../../../components/card/Event/EventAfterRegist';
const event = {
  name: "결혼식",
  date: "24.08.29",
  position: "서울시 강남구"
}
const before = {
  name: "이찬진",
  date: "24.08.29",
  detail: "고등학교 친구",
  price: "34000"
}
const after = {
  name: "이찬진",
  date: "24.08.29",
  detail: "고등학교 친구",
  price: "34000",
  tag: "friend"
}
export default function EventList() {
  return (
    <Container>
      <EventListCard event={event} />
      <EventBeforeRegist event={before} />
      <EventAfterRegist event={after} />
    </Container>
  )
}
