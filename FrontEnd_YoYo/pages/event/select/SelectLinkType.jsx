import { View, Text, StyleSheet } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import WhiteButton from '../../../components/common/WhiteButton'
import { MaterialCommunityIcons } from '@expo/vector-icons';

export default function SelectLinkType({ navigation }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  return (
    <Container>
      <View style={styles.innerContainer}>
        <WhiteButton text="QR코드" onPress={() => navigation.navigate('QrCode')}>
          <MaterialCommunityIcons name='qrcode-scan' size={80} />
        </WhiteButton>
        <WhiteButton text="링크전송" onPress={() => alert("링크복사")}>
          <MaterialCommunityIcons name='link-variant' size={80} />
        </WhiteButton>

      </View>
    </Container>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row'
  }
})