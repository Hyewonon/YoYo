import { View, TextInput, StyleSheet } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { MainStyle } from '../../constants/style';
import React, { useState } from 'react';
export default function SearchBar({ placeholder }) {
  const [keyword, setKeyword] = useState('')
  function pressCancelHandler() {
    setKeyword("");
  }
  return (
    <View style={styles.searchContainer}>
      <Ionicons
        name='search'
        size={20}
        style={{ marginRight: 8 }}
        color={MainStyle.colors.main}
      />
      <TextInput
        style={styles.input}
        placeholder={placeholder}
        maxLength={15}
        keyboardType='default'
        placeholderTextColor={MainStyle.colors.lightGray}
        autoCapitalize='none'
        textAlignVertical='center'
        autoCorrect={false}
        value={keyword}
        onChangeText={setKeyword}
      />
      <Ionicons
        name='close-sharp'
        size={20}
        style={{ marginLeft: 8 }}
        color={MainStyle.colors.main}
        onPress={pressCancelHandler}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  searchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: MainStyle.colors.main,
    borderRadius: 50,
    paddingHorizontal: 8,
    paddingVertical: 4,
    marginTop: 16,
  },
  input: {
    flex: 1,
    fontSize: 16,
    paddingVertical: 0, // 추가: 패딩 제거
  },
});
