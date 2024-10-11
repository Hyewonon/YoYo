import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";
// Notification 목록 조회
export async function getNotification(type) {
    const response = await axiosInstance.get(
        END_POINT.NOTIFICATION + `?type=${type}`
    );

    return response.data;
}
