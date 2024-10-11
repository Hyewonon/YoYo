import { View, Pressable, StyleSheet, FlatList } from "react-native";
import Header from "../../../components/header/Header";
import YoYoText from "../../../constants/YoYoText";
import IconButton from "../../../components/common/IconButton";
import { MainStyle } from "../../../constants/style";
import { useNavigation } from "@react-navigation/native";
import { useDispatch } from "react-redux";
import { logout } from "../../../store/slices/authSlice";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useEffect, useState } from "react";
import { deletePushToken } from "../../../apis/https/member";

export default function SettingList() {
    const [memberName, setMemberName] = useState("")
    const dispatch = useDispatch()
    const navigation = useNavigation();
    const settingList = [
        { id: "1", title: "개인정보 처리방침" },
        { id: "2", title: "로그아웃" },
    ];

    const navigationList = {
        1: () => navigation.navigate("Private"), // 실제 네비게이션 이동
        2: async () => {
            await deletePushToken()
            dispatch(logout())
        }, // 로그아웃을 처리할 함수
    };
    useEffect(() => {
        async function getMemberName() {
            const name = await AsyncStorage.getItem("memberName")
            setMemberName(name)
        }
        getMemberName()
    }, [])
    function clickItem(item) {
        const action = navigationList[item.id]; // id에 해당하는 액션을 찾아서 실행
        if (action) {
            action(); // 해당 액션이 존재하면 실행
        }
    }

    function renderItem({ item }) {
        return (
            <Pressable style={styles.item} onPress={() => clickItem(item)}>
                <YoYoText type={"subTitle"}>{item.title}</YoYoText>
                <IconButton
                    icon={"chevron-forward"}
                    size={24}
                    onPress={() => clickItem(item)}
                />
            </Pressable>
        );
    }
    return (
        <View style={styles.outerContainer}>
            <View style={styles.container}>
                <Header />
                <YoYoText type={"title"} bold>
                    {memberName}님, 반갑습니다.
                </YoYoText>
            </View>
            <View>
                <FlatList
                    data={settingList}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.id}
                    ItemSeparatorComponent={() => (
                        <View style={styles.separator} />
                    )}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    outerContainer: {
        backgroundColor: MainStyle.colors.white,
        flex: 1,
    },
    container: {
        padding: 24,
    },
    item: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        paddingHorizontal: 20,
        paddingVertical: 10,
    },
    separator: {
        height: 1,
        backgroundColor: MainStyle.colors.main,
    },
});