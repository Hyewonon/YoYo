import { View, Text, StyleSheet } from 'react-native'
import YoYoText from '../../constants/YoYoText'
import Button from '../common/Button'
import * as Clipboard from "expo-clipboard"
import { MainStyle } from '../../constants/style';
export default function EventLink({ eventId, setIsModalOpen }) {
  const copyToClipboard = async () => {
    await Clipboard.setStringAsync(`https://j11a308.p.ssafy.io/payment/checkout/${eventId}`);
    setIsModalOpen(false)
  };


  return (
    <View style={styles.outerContainer}>
      <View style={styles.modalContainer}>
        <YoYoText bold center type="md" color={MainStyle.colors.main}>지인에게 전송하여 마음을 받으세요!</YoYoText>
        <YoYoText type="desc" center bold >{`https://j11a308.p.ssafy.io/payment/checkout/${eventId}`}</YoYoText>
        <Button type="fill" radius={16} width={150} onPress={copyToClipboard}><YoYoText type="subContent" bold>복사</YoYoText></Button>
      </View>
    </View>
  )
}
const styles = StyleSheet.create({
  outerContainer: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0)', // 회색 배경에 투명도 20% 적용
    justifyContent: 'center',  // 세로로 중앙 정렬
    alignItems: 'center',      // 가로로 중앙 정렬
  },
  modalContainer: {
    width: "90%",              // 모달의 너비를 조정 (화면의 90%)
    height: 250,
    justifyContent: 'center',
    alignItems: 'center',
    gap: 24,
    backgroundColor: 'white',
    borderWidth: 2,
    borderRadius: 16,
    margin: 8
  },
  rowContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center'
  }
});
