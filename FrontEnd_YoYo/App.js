import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { MainStyle } from "./constants/style";
import MainPage from "./pages/mainpage/MainPage";
import ScheduleList from "./pages/schedule/list/ScheduleList";
import PayList from "./pages/payment/payList/PayList";
import SettingList from "./pages/setting/list/SettingList";
import { SafeAreaView } from "react-native-safe-area-context";
import { Ionicons } from "@expo/vector-icons";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Notification from "./pages/notification/Notification";
import Login from "./pages/member/login/Login";
import AccountRegist from "./pages/payment/regist/AccountRegist";
import EventList from "./pages/event/list/EventList";
import GiveAndTakeList from "./pages/giveAndtake/list/GiveAndTakeList";
import SendMoney from "./pages/payment/send/SendMoney";
import GiveAndTakeDetail from "./pages/giveAndtake/detail/GiveAndTakeDetail";
import EventDetail from "./pages/event/detail/EventDetail";
import GiveAndTakeRegist from "./pages/giveAndtake/regist/GiveAndTakeRegist";
import EventRegist from "./pages/event/regist/EventRegist";
import SignUp from "./pages/member/signup/SignUp";
import SelectGiveAndTake from "./pages/giveAndtake/regist/SelectGiveAndTake";
import SelectCard from "./pages/event/detail/SelectCard";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import RegistNewFriend from "./pages/event/detail/RegistNewFriend";
import SelectRegistType from "./pages/event/select/SelectRegistType";
import SelectLinkType from "./pages/event/select/SelectLinkType";
import QrCode from "./pages/event/select/QrCode";
import RegistPayPassword from "./pages/payment/password/RegistPayPassword";
import AfterPassword from "./pages/payment/password/AfterPassword.jsx";
import ScheduleDetail from "./pages/schedule/detail/ScheduleDetail";
import Private from "./pages/setting/agree/Private";
import ManageAccount from "./pages/setting/account/ManageAccount";
import YoYoText from "./constants/YoYoText.jsx";
import PayPage from "./pages/payment/PayPage.jsx";
import OcrPage from "./pages/ocr/OcrPage.jsx";
import OcrList from "./pages/ocr/list/OcrList";
import OcrSelect from "./pages/ocr/select/OcrSelect";
import { useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Provider, useDispatch, useSelector } from "react-redux";
import { store } from "./store/store.js";
import { setStoredAuth } from "./store/slices/authSlice.js";
import EventReceiveRegist from "./pages/event/regist/EventReceiveRegist.jsx";
const BottomTab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();
import * as Notifications from "expo-notifications";
import { savePushToken } from "./apis/https/member.js";
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
                        case "HomeTab":
                            icon = "home";
                            break;
                        case "ScheduleTab":
                            icon = "calendar-number-sharp";
                            break;
                        case "PaymentTab":
                            icon = "wallet";
                            break;
                        case "SettingTab":
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
                name="HomeTab"
                component={SharedStack} // SharedStack으로 변경
                initialParams={{ screenName: "Home" }} // 파라미터를 추가하여 사용할 수 있음
                options={{ tabBarLabel: "홈" }}
            />
            <BottomTab.Screen
                name="ScheduleTab"
                component={SharedStack} // SharedStack으로 변경
                initialParams={{ screenName: "Schedule" }} // 파라미터를 추가하여 사용할 수 있음
                options={{ tabBarLabel: "일정" }}
            />
            <BottomTab.Screen
                name="PaymentTab"
                component={SharedStack} // SharedStack으로 변경
                initialParams={{ screenName: "Payment" }} // 파라미터를 추가하여 사용할 수 있음
                options={{ tabBarLabel: "페이" }}
            />
            <BottomTab.Screen
                name="SettingTab"
                component={SharedStack} // SharedStack으로 변경
                initialParams={{ screenName: "Setting" }} // 파라미터를 추가하여 사용할 수 있음
                options={{ tabBarLabel: "설정" }}
            />
        </BottomTab.Navigator>
    );
}
function AuthStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                headerStyle: {
                    backgroundColor: MainStyle.colors.white,
                },
                headerTitleAlign: "center",
            }}
        >
            <Stack.Screen
                name="Login"
                component={Login}
                options={{
                    headerShown: false,
                }}
            />
            <Stack.Screen
                name="SignUp"
                component={SignUp}
                options={{
                    // headerShown: false,
                    title: "",
                }}
            />
        </Stack.Navigator>
    );
}
function SharedStack({ route }) {
    const { screenName } = route.params;
    return (
        <Stack.Navigator
            screenOptions={{
                headerStyle: {
                    backgroundColor: MainStyle.colors.white,
                },
                headerTitleAlign: "center",
            }}
        >
            {screenName === "Home" ? (
                <Stack.Screen
                    name="Home"
                    component={MainPage}
                    options={{
                        headerShown: false,
                    }}
                />
            ) : null}
            {screenName === "Schedule" ? (
                <Stack.Screen
                    name="Schedule"
                    component={ScheduleList}
                    options={{
                        headerShown: false,
                    }}
                />
            ) : null}
            {screenName === "Payment" ? (
                <Stack.Screen
                    name="Payment"
                    component={PayPage}
                    options={{
                        headerShown: false,
                    }}
                />
            ) : null}
            {screenName === "Setting" ? (
                <Stack.Screen
                    name="Setting"
                    component={SettingList}
                    options={{
                        headerShown: false,
                    }}
                />
            ) : null}

            <Stack.Screen
                name="Pay List"
                component={PayList}
                options={{
                    headerTitle: () => (
                        <YoYoText type="subTitle" logo>
                            Pay List
                        </YoYoText>
                    ),
                }}
            />

            <Stack.Screen
                name="EventList"
                component={EventList}
                options={{
                    title: "경조사 목록",
                }}
            />
            <Stack.Screen
                name="GiveAndTake"
                component={GiveAndTakeList}
                options={{
                    // headerShown: false,
                    title: "요요 목록보기",
                }}
            />
            <Stack.Screen name="YoYoDetail" component={GiveAndTakeDetail} />
            <Stack.Screen
                name="EventDetail"
                component={EventDetail}
                options={{ title: "" }}
            />

            <Stack.Screen
                name="QrCode"
                component={QrCode}
                options={{
                    title: "",
                    presentation: "modal",
                }}
            />

            <Stack.Screen
                name="GiveAndTakeDetail"
                component={GiveAndTakeDetail}
                options={{
                    // headerShown: false,
                    title: "요요 상세보기",
                }}
            />
            <Stack.Screen name="ScheduleDetail" component={ScheduleDetail} />
            <Stack.Screen
                name="Notification"
                component={Notification}
                options={{
                    // headerShown: false,
                    title: "",
                }}
            />
            <Stack.Screen
                name="Private"
                component={Private}
                options={{
                    // headerShown: false,
                    title: "개인정보처리방침",
                }}
            />
            <Stack.Screen
                name="ManageAccount"
                component={ManageAccount}
                options={{
                    // headerShown: false,
                    title: "나의 계좌",
                }}
            />
        </Stack.Navigator>
    );
}
function AuthenticatedStack() {
    return (
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
                name="EventRegist"
                component={EventRegist}
                options={{
                    // headerShown: false,
                    title: "",
                }}
            />
            <Stack.Screen
                name="EventReceiveRegist"
                component={EventReceiveRegist}
                options={{
                    title: "직접 등록",
                }}
            />
            <Stack.Screen
                name="GiveAndTakeRegist"
                component={GiveAndTakeRegist}
                options={{
                    title: "거래내역추가",
                }}
            />

            <Stack.Screen
                name="SelectGiveAndTake"
                component={SelectGiveAndTake}
                options={{
                    // headerShown: false,
                    title: "",
                }}
            />
            <Stack.Screen
                name="SelectRegistType"
                component={SelectRegistType}
                options={{
                    title: "",
                }}
            />
            <Stack.Screen
                name="SelectLinkType"
                component={SelectLinkType}
                options={{
                    title: "",
                }}
            />
            <Stack.Screen
                name="OCRPAGE"
                component={OcrPage}
                options={{
                    title: "",
                }}
            />
            <Stack.Screen
                name="OCRLIST"
                component={OcrList}
                options={{
                    title: "인식 결과",
                }}
            />
            <Stack.Screen
                name="OCRSELECT"
                component={OcrSelect}
                options={{
                    title: "",
                }}
            />
            <Stack.Screen
                name="계좌등록"
                component={AccountRegist}
                options={{
                    headerBackVisible: false,
                }}
            />
            <Stack.Screen name="돈보내기" component={SendMoney} />
            <Stack.Screen name="지인선택" component={SelectCard} />
            <Stack.Screen name="지인추가" component={RegistNewFriend} />
            <Stack.Screen
                name="RegistPayPassword"
                component={RegistPayPassword}
                options={{
                    headerShown: false,
                    presentation: "modal",
                }}
            />
            <Stack.Screen
                name="AfterPassword"
                component={AfterPassword}
                options={{
                    headerShown: false,
                    presentation: "modal",
                }}
            />
        </Stack.Navigator>
    );
}

function Navigation() {
    const { isAuthenticated } = useSelector((state) => state.auth);
    const linking = {
        prefixes: ["https://j11a308.p.ssafy.io", "yoyo://"],
        config: {
            screens: {
                돈보내기: "send-money/:eventId",
            },
        },
    };
    return (
        <NavigationContainer linking={linking}>
            {!isAuthenticated && <AuthStack />}
            {isAuthenticated && <AuthenticatedStack />}
        </NavigationContainer>
    );
}

function Root() {
    const dispatch = useDispatch();

    useEffect(() => {
        async function fetchToken() {
            const storedToken = await AsyncStorage.getItem("token");
            const storedMemberId = await AsyncStorage.getItem("memberId");
            const pushToken = await AsyncStorage.getItem("pushToken");

            // console.log(storedToken, "\n", storedMemberId, "\n", pushToken);
            if ((storedToken && storedMemberId, pushToken)) {
                await savePushToken(pushToken);
                // AsyncStorage에서 불러온 값으로 Redux 상태 업데이트
                dispatch(
                    setStoredAuth({
                        token: storedToken,
                        memberId: parseInt(storedMemberId),
                        pushToken: pushToken,
                    })
                );
            }
        }
        fetchToken();
    }, [dispatch]);

    return <Navigation />;
}

export default function App() {
    return (
        <>
            <SafeAreaView />
            <StatusBar style="dark" />
            <Provider store={store}>
                <GestureHandlerRootView style={{ flex: 1 }}>
                    <Root />
                </GestureHandlerRootView>
            </Provider>
        </>
    );
}
