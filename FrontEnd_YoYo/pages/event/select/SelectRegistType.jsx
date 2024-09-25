import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import WhiteButton from '../../../components/common/WhiteButton'
import { Ionicons } from '@expo/vector-icons';

export default function SelectRegistType({ navigation }) {
  return (
    <Container>
      <View style={styles.innerContainer}>
        <WhiteButton text="인식하기" onPress={() => navigation.navigate("OCRPAGE")}>
          <Ionicons name='camera-outline' size={80} />
        </WhiteButton>
        <WhiteButton text="직접등록" onPress={() => navigation.navigate("GiveAndTakeRegist", { type: 1 })}>
          <Ionicons name='create-outline' size={80} />
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