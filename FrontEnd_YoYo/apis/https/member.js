import { BASE_URL, END_POINT } from "../axiosConstants";
import { axiosInstance } from "./../axiosInstance";

//문자전송
export async function sendMessage(phoneNumber) {
    const response = await axiosInstance.post(
        END_POINT.MEMBER("send") + `?phoneNumber=${phoneNumber}`
    );
    return response.data.status === 200;
}
//문자인증
export async function checkMessage(phoneNumber, certificationCode) {
    try {
        await axiosInstance.post(
            END_POINT.MEMBER("verify") +
                `?phoneNumber=${phoneNumber}&code=${certificationCode}`
        );
        return true;
    } catch (error) {
        return false;
    }
}
//회원가입
export async function signUp(profile) {
    const response = await axiosInstance.post(
        END_POINT.MEMBER("register"),
        profile
    );
    return response.data.status === 201;
}
//로그인
export const login = async (phoneNumber, password) => {
    const response = await axiosInstance.post(END_POINT.MEMBER("login"), {
        phoneNumber: phoneNumber,
        password: password,
    });
    return response.data.data;
};
//FCM 토큰 저장
export async function savePushToken(pushToken) {
    const response = await axiosInstance.post(END_POINT.MEMBER("fcm-token"), {
        pushToken: pushToken,
    });
    return response.data.status === 200;
}
//FCM 토큰 삭제
export async function deletePushToken() {
    return await axiosInstance.delete(END_POINT.MEMBER("fcm-token"));
}
