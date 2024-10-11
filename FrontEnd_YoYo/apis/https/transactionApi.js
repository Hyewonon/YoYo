import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";

export const updateTransaction = async (id, data) => {
    return await axiosInstance.patch(END_POINT.TRANSACTIONS(id), data);
};
// 이벤트에 해당하는 거래내역 가져오기
export async function getTransaction(oppositeId) {
    const response = await axiosInstance.get(
        END_POINT.TRANSACTION_PATH("relation/") + `${oppositeId}`
    );

    return response.data.data;
}

// 거래내역 작성하기
export async function postTransaction(data) {
    const response = await axiosInstance.post(END_POINT.TRANSACTION, data);

    return response.data.data;
}

export async function deleteTransaction(transactionId) {
    const response = await axiosInstance.delete(
        END_POINT.TRANSACTIONS(transactionId)
    );

    return response.data.data;
}
//ocr이미지 전송
export async function sendOcrImage(uri) {
    const data = new FormData();
    data.append("imageFile", {
        uri: uri,
        name: "image",
        type: "image/jpg",
    });
    const response = await axiosInstance.post(
        END_POINT.TRANSACTIONS("ocr-image"),
        data,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }
    );
    return response.data.data;
}
//OCR-확정
export const confirmOcr = async (id, data) => {
    return await axiosInstance.post(
        END_POINT.TRANSACTIONS(`ocr-confirm/${id}`),
        data
    );
};
