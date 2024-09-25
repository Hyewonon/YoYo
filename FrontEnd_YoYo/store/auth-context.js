import AsyncStorage from "@react-native-async-storage/async-storage";
import { createContext, useState } from "react";

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

    function authenticate(token, id) {
        setMemberId(id);
        setAuthToken(token);
        AsyncStorage.setItem("token", token);
        AsyncStorage.setItem("memberId", id.toString());
    }
    function logout() {
        setAuthToken(null);
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
