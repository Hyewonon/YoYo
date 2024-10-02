import AsyncStorage from "@react-native-async-storage/async-storage";
import { createContext, useState } from "react";
import { axiosInterceptor } from "../apis/axiosInterceptor";
import { axiosInstance } from "../apis/axiosInstance";

export const AuthContext = createContext({
    token: "",
    memberId: -1,
    isAuthenticated: false,
    login: () => {},
    logout: () => {},
});
function AuthContextProvider({ children }) {
    const [authToken, setAuthToken] = useState();
    const [memberId, setMemberId] = useState();

    function authenticate(token, id, pushToken) {
        setMemberId(id);
        setAuthToken(token);
        AsyncStorage.setItem("token", token);
        AsyncStorage.setItem("memberId", id.toString());
        AsyncStorage.setItem('pushToken', pushToken);
    }

    function logout() {
        setAuthToken(null);
        setMemberId(null);
        AsyncStorage.removeItem("token");
        AsyncStorage.removeItem("memberId");
    }
    const value = {
        token: authToken,
        memberId: memberId,
        isAuthenticated: !!authToken,
        login: authenticate,
        logout: logout,
    };
    return (
        <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
    );
}
export default AuthContextProvider;
