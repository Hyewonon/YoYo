import { View, StyleSheet } from 'react-native'
import React from 'react'
import YoYoText from './../../../constants/YoYoText';
import BankCard from '../../../components/pay/BankCard';
const banks = [
  'KB국민은행', '신한은행', '하나은행', '우리은행', 'IBK기업은행', 'SC제일은행', 'NH농협은행', '카카오뱅크', '토스뱅크'
];
export default function SelectBank({ onPress }) {
  return (
    <>
      <YoYoText type="md" bold>계좌 등록을 위해{"\n"}은행을 선택해주세요</YoYoText>
      <View style={styles.bankContainer}>
        {banks.map((bank) => (
          <BankCard bank={bank} key={bank} onPress={onPress} />
        ))}
      </View>
    </>
  )
}
const styles = StyleSheet.create({
  bankContainer: {
    justifyContent: 'center',
    flexDirection: 'row',
    gap: 24,
    marginTop: 36,
    flexWrap: 'wrap'
  }
});