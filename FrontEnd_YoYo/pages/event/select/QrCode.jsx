import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import QRCode from 'react-native-qrcode-svg'
export default function QrCode() {
  const qrData = {
    link: 'https://www.youtube.com/watch?v=URBcer_Tf3I',
    description: 'Example description for QR code',
    title: 'Example QR Code',
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