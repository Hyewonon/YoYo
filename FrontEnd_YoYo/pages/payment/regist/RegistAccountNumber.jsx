import { View, StyleSheet, Modal } from 'react-native'
import React, { useState } from 'react'
import Input from './../../../components/common/Input';
import Button from './../../../components/common/Button';
import YoYoText from '../../../constants/YoYoText';
import AccountAgree from './AccountAgree';
import { TouchableOpacity } from 'react-native';

export default function RegistAccountNumber({ number, setNumber, onPress }) {
  const [isBottomUp, setIsBottomUp] = useState(false)
  return (
    <>
      <View style={styles.container}>
        <View style={styles.inputContainer}>
          <Input type="phoneNumber" text={number} onChange={setNumber} placeholder="계좌번호를 입력해주세요" />
        </View>
        <Button type={number.length > 10 ? "fill" : "inactive"} radius={30} width={80} onPress={() => setIsBottomUp(true)}><YoYoText type="md" bold>인증</YoYoText></Button>
      </View>
      <Modal visible={isBottomUp} animationType='slide'
        transparent={true}
        onRequestClose={() => setIsBottomUp(false)}>
        <TouchableOpacity style={styles.modalOverlay} onPress={() => setIsBottomUp(false)} />
        <View style={styles.modalContainer}>
          <AccountAgree onPress={onPress} setIsBottomUp={setIsBottomUp} />
        </View>
      </Modal>
    </>
  )
}
const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',

    gap: 8,
    flex: 1
  },
  inputContainer: {
    width: '80%'
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.5)', // semi-transparent background
  },
  modalContainer: {
    height: '40%', // 40% height from the bottom
    backgroundColor: 'white',
    borderTopLeftRadius: 32,
    borderTopRightRadius: 32,
    padding: 16,
    position: 'absolute',
    bottom: 0,
    width: '100%',
  },
})
