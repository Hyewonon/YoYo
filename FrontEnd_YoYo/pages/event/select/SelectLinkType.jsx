import { View, Text, StyleSheet, Modal } from 'react-native'
import React, { useState } from 'react'
import Container from '../../../components/common/Container'
import WhiteButton from '../../../components/common/WhiteButton'
import { MaterialCommunityIcons } from '@expo/vector-icons';
import EventLink from '../../../components/event/EventLink';

export default function SelectLinkType({ navigation, route }) {
  const event = route.params.event
  const [isModalOpen, setIsModalOpen] = useState(false)
  return (
    <>
      <Container>
        <View style={styles.innerContainer}>
          <WhiteButton text="QR코드" onPress={() => navigation.navigate('QrCode', { event: event })}>
            <MaterialCommunityIcons name='qrcode-scan' size={80} />
          </WhiteButton>
          <WhiteButton text="링크전송" onPress={() => setIsModalOpen(true)}>
            <MaterialCommunityIcons name='link-variant' size={80} />
          </WhiteButton>

        </View>
      </Container>
      <Modal visible={isModalOpen} animationType='fade'
        transparent={true}
        onRequestClose={() => setIsModalOpen(false)}>
        <EventLink eventId={event.eventId} setIsModalOpen={setIsModalOpen} />
      </Modal>
    </>
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