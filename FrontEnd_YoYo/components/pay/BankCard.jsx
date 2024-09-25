import { View, Image, StyleSheet, Pressable } from 'react-native'
import React from 'react'
import YoYoText from '../../constants/YoYoText'
const bankImages = {
  KB국민은행: require("../../assets/svg/banks/KB국민은행.png"),
  신한은행: require("../../assets/svg/banks/신한은행.png"),
  하나은행: require("../../assets/svg/banks/하나은행.png"),
  IBK기업은행: require("../../assets/svg/banks/IBK기업은행.png"),
  카카오뱅크: require("../../assets/svg/banks/카카오뱅크.png"),
  NH농협은행: require("../../assets/svg/banks/NH농협은행.png"),
  SC제일은행: require("../../assets/svg/banks/SC제일은행.png"),
  토스뱅크: require("../../assets/svg/banks/토스뱅크.png"),
  우리은행: require("../../assets/svg/banks/우리은행.png")
}
export default function BankCard({ bank, onPress }) {

  return (
    <View style={styles.outerContainer}>
      <Pressable android_ripple={{ color: "#ccc" }} style={styles.container} onPress={() => onPress(bank)}>
        <View style={styles.innerContiner}>
          <Image source={bankImages[bank]} />
          <YoYoText type="desc" bold center>{bank}</YoYoText>
        </View>
      </Pressable>
    </View>
  )
}
const styles = StyleSheet.create({
  outerContainer: {
    overflow: 'hidden',
    borderRadius: 16,
    elevation: 6,
    backgroundColor: 'white',

  },
  container: {
    width: 100,
    height: 100,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 16,
    padding: 8,
  },
  innerContiner: {
    justifyContent: 'center',
    alignItems: 'center',
  },
})
