import { StyleSheet, View, Pressable } from 'react-native'
import React from 'react'
import YoYoText from '../../../constants/YoYoText'
import Button from '../../common/Button';
import { MainStyle } from '../../../constants/style';
import { useNavigation } from '@react-navigation/native';
import MoneyCard from './MoneyCard';

export default function PayCard({ data, account, onPress }) {
  const navigation = useNavigation();
  function clickeGiveHandler() {
    navigation.navigate("돈보내기", {
      title: "옮기기"
    })
  }
  function clickChargeHandler() {
    navigation.navigate("돈보내기", {
      title: "충전하기"
    })
  }
  function clickPayListHandler() {
    navigation.navigate("Pay List")
  }
  return (
    <View style={styles.container}>
      <YoYoText type="subTitle" logo color={MainStyle.colors.white}>{data.memberName}님, 반갑습니다!</YoYoText>
      <MoneyCard account={account} money={data.balance} />
      {account ? <View style={styles.buttons}>
        <Pressable onPress={clickeGiveHandler}>
          <YoYoText type="md" bold color="white">옮기기</YoYoText>
        </Pressable>
        <YoYoText type="subTitle" bold color="white">|</YoYoText>
        <Pressable onPress={clickChargeHandler}>
          <YoYoText type="md" bold color="white">충전</YoYoText>
        </Pressable>
        <YoYoText type="subTitle" bold color="white">|</YoYoText>
        <Pressable onPress={clickPayListHandler}>
          <YoYoText type="md" bold color="white">내역</YoYoText>
        </Pressable>
      </View> :
        <View style={styles.buttonContainer}>
          <Button type="normal" width="50%" onPress={onPress} radius={24}><YoYoText type="desc" bold>계좌 등록하기</YoYoText></Button></View>}
    </View >
  )
}
const styles = StyleSheet.create({
  container: {
    width: '100%',
    height: 200,
    marginBottom: 24,

    paddingHorizontal: 12,
    justifyContent: 'center',
    // alignItems: 'center',
    backgroundColor: MainStyle.colors.main,
    borderRadius: 16,
    gap: 12,
    elevation: 5
  },
  moneyContainer: {
    flexDirection: 'row',
    width: '100%',
    justifyContent: "space-between",
    paddingVertical: 12,
    paddingHorizontal: 24,
    backgroundColor: MainStyle.colors.hover,
    alignItems: 'center',
    borderRadius: 8,
  },
  payContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4
  },
  buttons: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
    gap: 12
  },
  buttonContainer: {
    alignItems: 'center'
  }

})