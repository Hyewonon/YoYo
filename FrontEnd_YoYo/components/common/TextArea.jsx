import { View, Text, TextInput, StyleSheet } from 'react-native';
import React, { useState } from 'react';
import { MainStyle } from '../../constants/style';

export default function TextArea({ placeholder, onChange, isError, text }) {
  const [isFocused, setIsFocused] = useState(false);
  const [charCount, setCharCount] = useState(0); // 현재 글자 수를 추적하는 상태
  const maxLength = 30; // 최대 글자 수

  const handleFocus = () => setIsFocused(true);
  const handleBlur = () => setIsFocused(false);

  const handleTextChange = (text) => {
    setCharCount(text.length); // 글자 수 업데이트
    onChange(text); // 상위 컴포넌트로 텍스트 변경 이벤트 전달
  };

  const borderColor = isError
    ? MainStyle.colors.error // 에러가 있을 때의 색상
    : isFocused
      ? MainStyle.colors.main // 포커스될 때의 색상
      : MainStyle.colors.lightGray; // 기본 색상

  return (
    <View style={styles.inputContainer}>
      <TextInput
        style={[styles.input, { borderColor: borderColor }]}
        placeholder={placeholder}
        placeholderTextColor={MainStyle.colors.lightGray}
        onChangeText={handleTextChange}
        onFocus={handleFocus}
        onBlur={handleBlur}
        textAlignVertical='top'
        multiline={true}
        maxLength={maxLength}
        value={text}
      />
      {isError && <Text style={styles.error}>※ error Message</Text>}
      <Text style={styles.charCount}>
        {charCount} / {maxLength}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  inputContainer: {
    paddingHorizontal: 24,
    paddingVertical: 4,
    position: 'relative' // 상대적 위치 지정
  },
  input: {
    borderWidth: 1,
    fontSize: MainStyle.fontSize.md,
    paddingHorizontal: 8,
    paddingVertical: 8,
    borderRadius: 16,
    minHeight: 150,
  },
  error: {
    color: MainStyle.colors.error,
    fontSize: MainStyle.fontSize.subContent,
  },
  charCount: {
    position: 'absolute',
    bottom: 10,
    color: MainStyle.colors.gray,
    fontSize: MainStyle.fontSize.subContent,
    right: 30
  },
});
