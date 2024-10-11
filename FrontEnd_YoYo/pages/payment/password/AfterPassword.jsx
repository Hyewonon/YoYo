import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText'
import BigYoYo from '../../../assets/svg/BigYoYoIcon.svg'
import YoYoLoGo from '../../../assets/svg/YoYoLoGo.svg'
import { MainStyle } from '../../../constants/style'
import Button from '../../../components/common/Button'
export default function AfterPassword({ route, navigation }) {
  const data = route.params.data
  return (
    <>
      <Container>
        <View style={styles.innerContainer}>
          <YoYoText type="title" bold center color={MainStyle.colors.main}>{data.title}</YoYoText>
          <YoYoText type="md" bold center>{data.content}</YoYoText>
          <View style={styles.logoContainer}>
            <BigYoYo />
            <YoYoLoGo />
          </View>
        </View>
      </Container>
      <View style={styles.buttonContainer}>
        <Button type="fill" radius={16} width="90%" onPress={() => {
          navigation.navigate("HomeTab", { screen: "Home" });
        }}><YoYoText type="subTitle" bold>확인</YoYoText></Button>
      </View>
    </>
  )
}
const styles = StyleSheet.create({
  innerContainer: {
    marginTop: 124,
    gap: 36
  },
  logoContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 36,
    gap: 16
  },
  buttonContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 24
  }
})