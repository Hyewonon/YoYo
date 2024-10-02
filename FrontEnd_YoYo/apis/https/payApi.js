//계좌
import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "./../axiosInstance";
//계좌 등록,수정
export const registAccount = async (data) => {
    const response = await axiosInstance.post(END_POINT.ACCOUNT, data);
    return response.data;
};
//1원 송금
export const sendWon = async (data) => {
    const response = await axiosInstance.post(
        END_POINT.ACCOUTN_PATH("open"),
        data
    );
    return response.data;
};
//1원 송금 확인
export const checkWon = async (data) => {
    const response = await axiosInstance.post(
        END_POINT.ACCOUTN_PATH("check"),
        data
    );
    return response.data;
};
//페이 간 송금
export const managePay = async (path, data) => {
    const response = await axiosInstance.post(
        END_POINT.PAYMENT_PATH(path),
        data
    );
    return response.data;
};
//페이 거래 내역
export const getPayList = async (type) => {
    const response = await axiosInstance.get(
        END_POINT.PAYMENT_PATH("transaction") + `?payType=${type}`
    );
    return response.data;
};
//페이 잔액 조회
export const getPay = async () => {
    try {
        const response = await axiosInstance.get(END_POINT.PAYMENT);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};
