import { View, StyleSheet, Pressable, Animated } from "react-native";
import React, { useState, useEffect, useRef } from "react";
import { MainStyle } from "../../../constants/style";
import Funeral from "../../../assets/svg/funeral.svg";
import Marry from "../../../assets/svg/Marry.svg";
import Cake from "../../../assets/svg/Cake.svg";
import GiveAndTake from "../../../assets/svg/GiveAndTake.svg";
import YoYoText from "../../../constants/YoYoText";

const imageArray = [Funeral, Marry, Cake]; // 자동으로 변경될 이미지 배열

export default function MainPageCard({ title, subTitle, type, onPress }) {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const fadeAnimOut = useRef(new Animated.Value(1)).current; // 현재 이미지의 페이드 아웃 값
    const fadeAnimIn = useRef(new Animated.Value(0)).current; // 다음 이미지의 페이드 인 값

    useEffect(() => {
        const interval = setInterval(() => {
            // 이미지 페이드 아웃 및 인 애니메이션 실행
            Animated.sequence([
                // 페이드 아웃 애니메이션
                Animated.timing(fadeAnimOut, {
                    toValue: 0,
                    duration: 2500,
                    useNativeDriver: true,
                }),
                // 이미지 인덱스 변경 후 페이드 인 애니메이션
                Animated.timing(fadeAnimIn, {
                    toValue: 1,
                    duration: 2000,
                    useNativeDriver: true,
                })
            ]).start(() => {
                // 페이드 애니메이션 완료 후 이미지 전환
                setCurrentImageIndex((prevIndex) => {
                    const nextIndex = (prevIndex + 1) % imageArray.length;

                    // 다음 이미지를 위해 페이드 애니메이션 값 초기화
                    fadeAnimOut.setValue(1); // 페이드 아웃 애니메이션 값 리셋
                    fadeAnimIn.setValue(0);  // 페이드 인 애니메이션 값 리셋

                    return nextIndex;
                });
            });
        }, 5000); // 5초마다 이미지 변경

        return () => clearInterval(interval); // 컴포넌트가 언마운트될 때 interval 클리어
    }, []);

    const CurrentImage = imageArray[currentImageIndex]; // 현재 표시할 이미지
    const NextImage = imageArray[(currentImageIndex + 1) % imageArray.length]; // 다음 이미지

    return (
        <View style={styles.cardContainer}>
            <Pressable
                onPress={onPress}
                style={({ pressed }) => [
                    styles.pressableContainer,
                    pressed && styles.pressed,
                ]}
                android_ripple={{ color: MainStyle.colors.hover }}
            >
                <View style={styles.container}>
                    <View style={styles.textContainer}>
                        <YoYoText type="title" logo>
                            {title}
                        </YoYoText>
                        <YoYoText type="md">{subTitle}</YoYoText>
                    </View>
                    <View style={styles.imageContainer}>
                        {type === "yoyo" ? (
                            <GiveAndTake />
                        ) : (
                            <View style={{ position: 'relative' }}>
                                {/* 현재 이미지 */}
                                <Animated.View style={[styles.image, { opacity: fadeAnimOut, position: 'absolute' }]}>
                                    <CurrentImage />
                                </Animated.View>

                                {/* 다음 이미지 */}
                                <Animated.View style={[styles.image, { opacity: fadeAnimIn }]}>
                                    <NextImage />
                                </Animated.View>
                            </View>
                        )}
                    </View>
                </View>
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    cardContainer: {
        borderRadius: 16,
        overflow: "hidden",
        marginBottom: 24,
        elevation: 5,
        backgroundColor: "white",
    },
    pressableContainer: {
        borderRadius: 16,
    },
    container: {
        flexDirection: "row",
        borderRadius: 16,
        borderWidth: 2,
        borderColor: MainStyle.colors.main,
        height: 180,
        paddingLeft: 16,
        paddingRight: 8,
        paddingTop: 16,
        paddingBottom: 8,
    },
    textContainer: {
        flex: 1,
    },
    imageContainer: {
        justifyContent: "center",
        alignItems: "center",
        overflow: "hidden",
        position: "relative", // 이미지 겹침을 위해 상대 위치 설정
    },
    image: {
        width: "100%",
        height: "100%",
    },
    pressed: {
        opacity: 0.5,
    },
});
