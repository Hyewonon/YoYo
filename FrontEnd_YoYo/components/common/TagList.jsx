import { View, StyleSheet, Pressable } from 'react-native';
import { MainStyle } from '../../constants/style';
import React from 'react'
import Tag from './Tag';

export default function TagList({ onPress, selectedTag, size, all }) {
  let tag = [
    { type: "family", name: "가족" },
    { type: "friend", name: "친구" },
    { type: "company", name: "직장" },
    { type: "etc", name: "기타" }
  ];
  if (all) {
    tag = [
      { type: "all", name: "전체" },
      { type: "family", name: "가족" },
      { type: "friend", name: "친구" },
      { type: "company", name: "직장" },
      { type: "etc", name: "기타" }
    ];
  }
  return (

    <View style={styles.tagContainer}>
      {tag.map((item) =>
        <View key={item.type} style={{ borderColor: MainStyle.colors.main, borderWidth: selectedTag === item.type ? 4 : 0, borderRadius: 32 }}>
          <Pressable onPress={() => onPress(item.type)}>
            <Tag type={item.type} width={size}>{item.name}</Tag>
          </Pressable>
        </View>)}
    </View>
  )
}

const styles = StyleSheet.create({

  tagContainer: {
    marginVertical: 12,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center'
  }
});