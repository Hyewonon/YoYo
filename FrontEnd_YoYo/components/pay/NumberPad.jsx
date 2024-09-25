import { View, Pressable, StyleSheet } from 'react-native'
import React, { useEffect, useState } from 'react'
import { MainStyle } from '../../constants/style'
import NumberPadButton from './NumberPadButton'
import { Ionicons } from '@expo/vector-icons';
function shuffle(nums) {
  let num_length = nums.length
  while (num_length) {
    let random_index = Math.floor(num_length-- * Math.random())
    let temp = nums[random_index]
    nums[random_index] = nums[num_length]
    nums[num_length] = temp
  }
  return nums
}
export default function NumberPad({ setPayPassword, next }) {
  let nums_init = Array.from({ length: 10 }, (_, k) => k)
  const [nums, setNums] = useState(nums_init)
  useEffect(() => {
    setNums(shuffle([...nums_init]));
  }, [])
  const [nowIdx, setNowIdx] = useState(0);
  const [isIndexSix, setIsIndexSix] = useState(false)
  function clickNumberHandler(num) {
    setPayPassword((prev) => {
      const updated = [...prev]
      updated[nowIdx] = num
      return updated
    })
    if (nowIdx < 5)
      setNowIdx(nowIdx + 1);
    else {
      setIsIndexSix(true)
    }
  }
  function eraseAll() {
    setPayPassword([-1, -1, -1, -1, -1, -1])
    setNowIdx(0)
  }
  function eraseOne() {
    if (nowIdx > 0) {
      setNowIdx(nowIdx - 1)
      setPayPassword((prev) => {
        const updated = [...prev]
        updated[nowIdx - 1] = -1
        return updated
      })
    } else {
      return
    }
  }
  useEffect(() => {
    if (isIndexSix) {
      next();
      setNowIdx(0);
      setNums(shuffle([...nums_init]))
      setIsIndexSix(false)
    }
  }, [isIndexSix])
  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        {nums.map((num, idx) => {
          if (idx === 9) {
            return (
              <>
                <Pressable
                  key={`eraseAll-${idx}`}
                  style={({ pressed }) => [styles.iconContainer, pressed && styles.pressed]}
                  onPress={eraseAll}>
                  <Ionicons name="close" color="white" size={24} />
                </Pressable>
                <NumberPadButton
                  key={`number-${idx}`}
                  number={num}
                  onPress={() => clickNumberHandler(num)}
                />
                <Pressable
                  key={`eraseOne-${idx}`}
                  style={({ pressed }) => [styles.iconContainer, pressed && styles.pressed]}
                  onPress={eraseOne}>
                  <Ionicons name="backspace-outline" color="white" size={24} />
                </Pressable>
              </>
            );
          }
          return (
            <NumberPadButton
              key={`number-${idx}`}
              number={num}
              onPress={() => clickNumberHandler(num)}
            />
          );
        })}
      </View>
    </View>
  )
}
const styles = StyleSheet.create({
  container: {
    backgroundColor: MainStyle.colors.main,
    width: '100%',
    flex: 1.5,
  },
  innerContainer: {
    marginVertical: 80,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    alignItems: 'center'
  },
  iconContainer: {
    width: "33%",
    height: "33%",
    justifyContent: 'center',
    alignItems: 'center'
  },
  pressed: {
    backgroundColor: MainStyle.colors.hover
  }
})