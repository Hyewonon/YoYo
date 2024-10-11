import AsyncStorage from "@react-native-async-storage/async-storage";
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    token: "",
    memberId: -1,
    pushToken: "",
    isAuthenticated: false,
};

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        login(state, action) {
            state.token = action.payload.token;
            state.memberId = action.payload.memberId;
            state.pushToken = action.payload.pushToken;
            state.isAuthenticated = true;

            AsyncStorage.setItem("token", action.payload.token);
            AsyncStorage.setItem(
                "memberId",
                action.payload.memberId.toString()
            );
            AsyncStorage.setItem("pushToken", action.payload.pushToken);
        },
        logout(state) {
            state.token = "";
            state.memberId = -1;
            state.isAuthenticated = false;

            AsyncStorage.removeItem("token");
            AsyncStorage.removeItem("memberId");
            AsyncStorage.removeItem("pushToken");
        },
        setStoredAuth(state, action) {
            state.token = action.payload.token;
            state.memberId = action.payload.memberId;
            state.pushToken = action.payload.pushToken;
            state.isAuthenticated = !!action.payload.token;
        },
    },
});
export const { login, logout, setStoredAuth } = authSlice.actions;

export default authSlice.reducer;
