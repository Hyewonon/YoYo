import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import QRCode from 'react-native-qrcode-svg'
export default function QrCode({ route }) {
  const event = route.params.event
  const qrData = {
    link: `https://j11a308.p.ssafy.io/payment/checkout/${event.eventId}`,
    description: `${event.title}에 참석해주셔서 감사합니다.`,
    title: '마음을 전해주세요.',
    createdAt: new Date().toISOString()
  };
  return (
    <Container>
      <YoYoText type="title" bold center>마음을 전해주셔서{"\n"} 감사해Yo!</YoYoText>
      <View style={styles.qrContainer}>
        <QRCode value={qrData.link} size={256} />
      </View>
    </Container>
  )
}
const styles = StyleSheet.create({
  qrContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 80
  }
})
