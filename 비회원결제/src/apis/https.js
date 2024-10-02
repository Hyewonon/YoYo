import { END_POINT } from "./axiosConstants";
import { axiosInstance } from "./axiosInstance";

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
