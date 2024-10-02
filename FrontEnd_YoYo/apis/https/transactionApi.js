import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";

// 이벤트에 해당하는 거래내역 가져오기
export async function getTransaction(oppositeId) {
    const response = await axiosInstance.get(
        END_POINT.TRANSACTION_PATH("relation/") + `${oppositeId}`
    );

    return response.data.data;
}

// 이벤트에 해당하는 거래내역 가져오기
export async function postTransaction(data) {
    const response = await axiosInstance.post(END_POINT.TRANSACTION, { data });

    return response.data.data;
}
