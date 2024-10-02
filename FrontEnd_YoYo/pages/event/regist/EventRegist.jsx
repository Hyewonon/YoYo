import { View, StyleSheet, Modal, Alert } from 'react-native'
import React, { useContext, useEffect, useLayoutEffect, useState } from 'react'
import Container from '../../../components/common/Container'
import YoYoText from '../../../constants/YoYoText';
import { MainStyle } from '../../../constants/style';
import Next from '../../../components/common/Next';
import IconButton from '../../../components/common/IconButton';
import EventType from '../../../components/event/regist/EventType';
import Input from '../../../components/common/Input';
import { registEvent } from '../../../apis/https/eventApi';
import { AuthContext } from '../../../store/auth-context';

export default function EventRegist({ navigation }) {
  const authCtx = useContext(AuthContext)
  const [stage, setStage] = useState(0);
  const [isActive, setIsActive] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [event, setEvent] = useState({
    eventType: "",
    title: "",
    location: "",
    startAt: "",
    endAt: ""
  })
  useEffect(() => {
    if (stage === 1) {
      if (event.title.length > 0) {
        setIsActive(true);
      } else {
        setIsActive(false);
      }
    }
  }, [event.title]);
  useEffect(() => {
    if (stage === 2) {
      if (event.location.length > 0 && event.startAt.length === 10 && event.endAt.length === 10) {
        setIsActive(true);
      } else {
        setIsActive(false);
      }
    }
  }, [event.location, event.startAt, event.endAt]);

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (<Next isActive={isActive} onPress={clickNextButton} final={stage === 2} />),
      headerLeft: () => (<IconButton icon="arrow-back-outline" size={24} onPress={clickPrevButton} />)
    });
  }, [navigation, stage, isActive]);
  async function clickNextButton() {
    if (stage < 2 && isActive) {
      setStage(stage + 1);
      setIsActive(false);
    } else if (stage == 2 && isActive) {
      try {
        const data = {
          eventType: event.eventType,
          title: event.title,
          location: event.location,
          startAt: new Date(event.startAt),
          endAt: new Date(event.endAt)
        }
        await registEvent(data)
        Alert.alert("등록 완료", "이벤트가 등록되었습니다!", [
          {
            text: "확인",
            onPress: () => navigation.navigate("EventList", {
              member: authCtx.memberId
            }),
          },
        ])
      } catch (error) {
        Alert.alert("등록 실패", "다시 등록해주세요.", [
          {
            text: "확인",
            onPress: () => {
              setStage(0)
              setEvent({
                eventType: "",
                title: "",
                location: "",
                startAt: "",
                endAt: ""
              })
            }
          },
        ])
      }

    }
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
  return (
    <Container>
      <View>
        <YoYoText type="subTitle" bold color={MainStyle.colors.main}>{stage === 0 ? "이벤트 유형" : stage === 1 ? "제목" : "상세 정보"}</YoYoText>
      </View>
      <View style={styles.container}>
        {stage === 0 && <EventType setIsActive={setIsActive} event={event} setEvent={setEvent} />}
        {stage === 1 && <Input placeholder="행사명을 입력해주세요." type="default" text={event.title} onChange={(text) => {
          setEvent((prev) => ({
            ...prev,
            title: text
          }))
        }} />}
        {stage === 2 &&
          <>
            <YoYoText type="md" bold>주소</YoYoText>
            <View style={styles.innerContainer}>
              <View style={styles.inputContainer}>
                <Input placeholder="주소를 입력해주세요." type="default" text={event.location} onChange={(text) => {
                  setEvent((prev) => ({
                    ...prev,
                    location: text
                  }))
                }} />
              </View>
              <IconButton icon="search" onPress={clickAddressButton} size={24} />
            </View>
            <YoYoText type="md" bold>기간</YoYoText>
            <View style={styles.innerContainer}>
              <View style={styles.dayContainer}>
                <Input placeholder="시작일" type="default" text={event.startAt} onChange={(text) => {
                  setEvent((prev) => ({
                    ...prev,
                    startAt: text
                  }))
                }} />
              </View>
              <IconButton icon="calendar-outline" onPress={clickCalendarButton} size={24} />
              <YoYoText type="md" bold>{"  "}~{"  "}</YoYoText>
              <View style={styles.dayContainer}>
                <Input placeholder="종료일" type="default" text={event.endAt} onChange={(text) => {
                  setEvent((prev) => ({
                    ...prev,
                    endAt: text
                  }))
                }} />
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
