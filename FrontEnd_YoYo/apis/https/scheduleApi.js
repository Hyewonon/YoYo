import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";
// Schedule 목록 조회
export async function getSchedule() {
    try {
        const response = await axiosInstance.get(END_POINT.SCHEDULE);
        return response.data;
    } catch (error) {
        return false;
    }
}
// Schedule 등록
export async function registerSchedule(notificationId, isRegister) {
    try {
        const response = await axiosInstance.patch(END_POINT.NOTIFICATION, {
            notificationId,
            isRegister,
        });

        return response.status === 201;
    } catch (error) {
        console.error("Error registering schedule:", error);
        return false;
    }
}

export async function getDetailSchedule(memberId) {
    try {
        //TODO: 거래내역을 가지고 와야 함

        return response.status === 201;
    } catch (error) {
        console.error("Error registering schedule:", error);
        return false;
    }
}
