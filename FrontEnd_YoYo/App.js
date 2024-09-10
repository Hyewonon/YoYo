import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { NavigationContainer } from "@react-navigation/native";
import { MainStyle } from "./constants/style";
import MainPage from "./pages/mainpage/MainPage";
import ScheduleList from "./pages/schedule/list/ScheduleList";
import PayList from "./pages/payment/payList/PayList";
import SettingList from "./pages/setting/list/SettingList";
import { StyleSheet, Text, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { Ionicons } from "@expo/vector-icons";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import Notification from "./pages/notification/Notification";
import Login from "./pages/member/login/Login";
import YoYoText from "./constants/YoYoText";
import AccountRegist from "./pages/payment/regist/AccountRegist";
import EventList from "./pages/event/list/EventList";
import GiveAndTakeList from "./pages/giveAndtake/list/GiveAndTakeList";
import SendMoney from "./pages/payment/send/SendMoney";
import ChargeMoney from "./pages/payment/send/ChargeMoney";
import GiveAndTakeDetail from "./pages/giveAndtake/detail/GiveAndTakeDetail";
import EventDetail from "./pages/event/detail/EventDetail";
import PhoneNumber from "./pages/member/signup/PhoneNumber";
import Password from "./pages/member/signup/Password";
import UserInfo from "./pages/member/signup/UserInfo";

const BottomTab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();
const TopTab = createMaterialTopTabNavigator();
function TopTabBar() {
    <TopTab.Navigator>
        <TopTab.Screen name="schedule" component={Notification} />
        <TopTab.Screen name="money" component={Notification} />
    </TopTab.Navigator>;
}

function BottomTabBar() {
    return (
        <BottomTab.Navigator
            screenOptions={({ route }) => ({
                unmountOnBlur: true,
                headerStyle: {
                    backgroundColor: MainStyle.colors.white,
                },
                headerTintColor: MainStyle.colors.black,
                tabBarStyle: {
                    backgroundColor: MainStyle.colors.white,
                    height: 60,
                    borderTopColor: MainStyle.colors.lightGray,
                    borderTopWidth: 2,
                },
                tabBarLabelStyle: {
                    fontSize: 12,
                    fontWeight: "bold",
                    marginBottom: 6,
                },
                tabBarActiveTintColor: MainStyle.colors.main,
                tabBarInactiveTintColor: MainStyle.colors.lightGray,
                headerShown: false,
                tabBarIcon: ({ focused }) => {
                    let icon;

                    switch (route.name) {
                        case "Home":
                            icon = "home";
                            break;
                        case "Schedule":
                            icon = "calendar-number-sharp";
                            break;
                        case "Payment":
                            icon = "wallet";
                            break;
                        case "Setting":
                            icon = "settings";
                            break;
                    }

                    return (
                        <Ionicons
                            name={icon}
                            size={30}
                            color={
                                focused
                                    ? MainStyle.colors.main
                                    : MainStyle.colors.lightGray
                            }
                        />
                    );
                },
            })}
        >
            <BottomTab.Screen
                name="Home"
                component={MainPage}
                options={{ tabBarLabel: "홈" }}
            />
            <BottomTab.Screen
                name="Schedule"
                component={ScheduleList}
                options={{ tabBarLabel: "일정" }}
            />
            <BottomTab.Screen
                name="Payment"
                component={Login}
                options={{ tabBarLabel: "페이" }}
            />
            <BottomTab.Screen
                name="Setting"
                component={SettingList}
                options={{ tabBarLabel: "설정" }}
            />
        </BottomTab.Navigator>
    );
}
export default function App() {
    return (
        <>
            <SafeAreaView style={styles.header}>
                <YoYoText type="mainTitle" bold>
                    YOYO
                </YoYoText>
            </SafeAreaView>
            <NavigationContainer>
                <Stack.Navigator
                    screenOptions={{
                        headerStyle: {
                            backgroundColor: MainStyle.colors.white,
                        },
                        headerTitleAlign: "center",
                    }}
                >
                    <Stack.Screen
                        name="BottomTabBar"
                        component={BottomTabBar}
                        options={{
                            headerShown: false,
                        }}
                    />
                    <Stack.Screen
                        name="TopTabBar"
                        component={TopTabBar}
                        options={{
                            headerShown: false,
                        }}
                    />
                    <Stack.Screen name="은행 선택" component={AccountRegist} />
                    <Stack.Screen name="Pay List" component={PayList} />
                    <Stack.Screen name="옮기기" component={SendMoney} />
                    <Stack.Screen name="충전" component={ChargeMoney} />
                    <Stack.Screen name="EventList" component={EventList} />
                    <Stack.Screen
                        name="GiveAndTake"
                        component={GiveAndTakeList}
                    />
                    <Stack.Screen
                        name="YoYoDetail"
                        component={GiveAndTakeDetail}
                    />
                    <Stack.Screen name="EventDetail" component={EventDetail} />
                    <Stack.Screen name="PhoneNumber" component={PhoneNumber} />
                    <Stack.Screen name="Password" component={Password} />
                    <Stack.Screen name="UserInfo" component={UserInfo} />
                    <Stack.Screen name="Login" component={Login} />
                </Stack.Navigator>
            </NavigationContainer>
        </>
    );
}
const styles = StyleSheet.create({
    header: {
        paddingHorizontal: 24,
        paddingVertical: 8,
    },
});
