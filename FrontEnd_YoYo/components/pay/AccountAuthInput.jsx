import React, { forwardRef } from 'react';
import { View, TextInput, StyleSheet } from 'react-native';
import { MainStyle } from '../../constants/style';

// forwardRef를 사용하여 ref를 받는 컴포넌트로 변경
const AccountAuthInput = forwardRef(({ number, onChangeText }, ref) => {
  return (
    <TextInput
      ref={ref} // ref를 TextInput에 전달
      maxLength={1}
      value={number}
      keyboardType="number-pad"
      onChangeText={onChangeText}
      style={styles.input}
    />
  );
});

const styles = StyleSheet.create({
  input: {
    width: 64,
    height: 64,
    padding: 12,
    fontSize: 32,
    borderWidth: 2,
    borderColor: MainStyle.colors.main,
    borderRadius: 16,
    textAlign: 'center',
  },
});

export default AccountAuthInput;
