import { View, StyleSheet, Modal } from 'react-native'
import React, { useEffect, useLayoutEffect, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText';
import { MainStyle } from '../../../constants/style';
import Next from '../../../components/common/Next';
import IconButton from '../../../components/common/IconButton';
import EventType from '../../../components/event/regist/EventType';
import Input from '../../../components/common/Input';

export default function EventRegist({ navigation }) {
  const [stage, setStage] = useState(0);
  const [isActive, setIsActive] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [title, setTitle] = useState("");
  const [address, setAddress] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  function clickNextButton() {
    if (stage < 3 && isActive)
      setStage(stage + 1);
    setIsActive(false);
  }

  function clickPrevButton() {
    if (stage === 0) {
      navigation.goBack();
    } else {
      setStage(stage - 1);
    }
  }

  function clickAddressButton() {
    setIsModalOpen(true);
  }

  function clickCalendarButton() {
    setIsModalOpen(true)
  }

  useEffect(() => {
    if (stage === 1) {
      if (title.length > 0) {
        setIsActive(true);
      } else {
        setIsActive(false);
      }
    }
  }, [title]);

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (<Next isActive={isActive} onPress={clickNextButton} final={stage === 2} />),
      headerLeft: () => (<IconButton icon="arrow-back-outline" size={24} onPress={clickPrevButton} />)
    });
  }, [navigation, stage, isActive]);

  return (
    <Container>
      <View>
        <YoYoText type="subTitle" bold color={MainStyle.colors.main}>{stage === 0 ? "이벤트 유형" : stage === 1 ? "제목" : "상세 정보"}</YoYoText>
      </View>
      <View style={styles.container}>
        {stage === 0 && <EventType setIsActive={setIsActive} />}
        {stage === 1 && <Input placeholder="행사명을 입력해주세요." type="default" text={title} onChange={setTitle} />}
        {stage === 2 &&
          <>
            <YoYoText type="md" bold>주소</YoYoText>
            <View style={styles.innerContainer}>
              <View style={styles.inputContainer}>
                <Input placeholder="주소를 입력해주세요." type="default" text={address} onChange={setAddress} />
              </View>
              <IconButton icon="search" onPress={clickAddressButton} size={24} />
            </View>
            <YoYoText type="md" bold>기간</YoYoText>
            <View style={styles.innerContainer}>
              <View style={styles.dayContainer}>
                <Input placeholder="시작일" type="default" text={startDate} onChange={setStartDate} />
              </View>
              <IconButton icon="calendar-outline" onPress={clickCalendarButton} size={24} />
              <YoYoText type="md" bold>{"  "}~{"  "}</YoYoText>
              <View style={styles.dayContainer}>
                <Input placeholder="종료일" type="default" text={endDate} onChange={setEndDate} />
              </View>
              <IconButton icon="calendar-outline" onPress={clickCalendarButton} size={24} />
            </View>
          </>
        }
      </View>
    </Container>
  );
}

const styles = StyleSheet.create({
  container: {
    marginTop: 36
  },
  innerContainer: {
    width: "100%",
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 24
  },
  inputContainer: {
    width: '95%'
  },
  dayContainer: {
    width: '40%'
  },
  modalContainer: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20
  }
});
