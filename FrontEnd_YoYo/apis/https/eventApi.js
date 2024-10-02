import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "./../axiosInstance";
//이벤트 목록 조회
export async function getEventList() {
    const response = await axiosInstance.get(END_POINT.EVENT);
    return response.data;
}
//이벤트 생성
export async function registEvent(data) {
    return await axiosInstance.post(END_POINT.EVENT, data);
}
//이벤트 상세 조회
export async function getEventDetail(id) {
    const response = await axiosInstance.get(END_POINT.EVENT_PATH(id));
    return response.data;
}
//이벤트 수정
export async function updateEvent(id, data) {
    return await axiosInstance.patch(END_POINT.EVENT_PATH(id), data);
}
//이벤트 검색
export async function searchEvent(keyword) {
    const response = await axiosInstance.get(
        END_POINT.EVENT_PATH("search") + `?keyword=${keyword}`
    );
    return response.data;
}
